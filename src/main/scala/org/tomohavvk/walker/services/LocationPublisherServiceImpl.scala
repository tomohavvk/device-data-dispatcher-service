package org.tomohavvk.walker.services

import cats.effect.kernel.Clock
import cats.effect.kernel.Sync
import cats.implicits.catsSyntaxApplicativeError
import cats.implicits.catsSyntaxFlatMapOps
import cats.implicits.catsSyntaxFlatten
import cats.implicits.toFlatMapOps
import cats.implicits.toFunctorOps
import fs2.kafka.ProducerRecord
import fs2.kafka.ProducerRecords
import io.odin.Logger
import org.tomohavvk.walker.EventProducer
import org.tomohavvk.walker.protocol.Types.DeviceId
import org.tomohavvk.walker.protocol.Types.EventId
import org.tomohavvk.walker.protocol.Types.Key
import org.tomohavvk.walker.protocol.Types.UnixTime
import org.tomohavvk.walker.protocol.error.views.AcknowledgeView
import org.tomohavvk.walker.protocol.events.DeviceLocationEvent
import org.tomohavvk.walker.protocol.events.Event
import org.tomohavvk.walker.protocol.events.Metadata
import org.tomohavvk.walker.utils.ContextFlow
import org.tomohavvk.walker.utils.anySyntax
import org.tomohavvk.walker.utils.liftFSyntax
import org.tomohavvk.walker.protocol.DeviceLocation

import java.util.UUID

class LocationPublisherServiceImpl[F[_]: Sync: Clock](
  eventProducer: EventProducer[F, Key, Event],
  logger:        Logger[ContextFlow[F, *]])
    extends LocationPublisherService[F] {
  import eventProducer._

  override def publish(deviceId: DeviceId, locations: List[DeviceLocation]): ContextFlow[F, AcknowledgeView] =
    logger.debug(s"Locations size: ${locations.size}") >>
      makeEvent(deviceId, locations)
        .flatMap { event =>
          producer.produce(ProducerRecords.one(ProducerRecord(topic, event.meta.id.value, event))).flatten.attempt
        }
        .liftFlow
        .flatMap {
          case Right(_) => AcknowledgeView(true).rightT
          case Left(error) =>
            logger.debug(error.getLocalizedMessage) >>
              AcknowledgeView(false).rightT
        }

  private def makeEvent(deviceId: DeviceId, locations: List[DeviceLocation]): F[Event] =
    for {
      uuid     <- Sync[F].delay(UUID.randomUUID())
      realTime <- Clock[F].realTimeInstant.map(_.toEpochMilli)
    } yield {
      val eventId    = EventId(uuid)
      val producedAt = UnixTime(realTime)

      val metadata = Metadata(eventId, producedAt)
      val event    = DeviceLocationEvent(deviceId, locations)

      Event(event, metadata)
    }
}
