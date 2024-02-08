package org.tomohavvk.walker.services

import cats.effect.kernel.Clock
import cats.effect.kernel.Sync
import cats.implicits.catsSyntaxApplyOps
import cats.implicits.catsSyntaxFlatten
import cats.implicits.toFlatMapOps
import cats.implicits.toFunctorOps
import fs2.kafka.ProducerRecord
import fs2.kafka.ProducerRecords
import io.odin.Logger
import org.tomohavvk.walker.EventProducer
import org.tomohavvk.walker.protocol.Types.EventId
import org.tomohavvk.walker.protocol.Types.Key
import org.tomohavvk.walker.protocol.Types.UnixTime
import org.tomohavvk.walker.protocol.error.views.AcknowledgeView
import org.tomohavvk.walker.protocol.events.DeviceLocationEvent
import org.tomohavvk.walker.protocol.events.Event
import org.tomohavvk.walker.protocol.events.Metadata
import org.tomohavvk.walker.protocol.requests.DeviceLocationRequest
import org.tomohavvk.walker.utils.ContextFlow
import org.tomohavvk.walker.utils.appResultSyntax
import org.tomohavvk.walker.utils.liftFSyntax

import java.util.UUID

class LocationPublisherServiceImpl[F[_]: Sync: Clock](
  eventProducer: EventProducer[F, Key, Event],
  logger:        Logger[ContextFlow[F, *]])
    extends LocationPublisherService[F] {
  import eventProducer._

  override def publish(request: DeviceLocationRequest): ContextFlow[F, AcknowledgeView] =
    logger.debug(request.toString) *>
      makeEvent(request)
        .flatTap { event =>
          producer.produce(ProducerRecords.one(ProducerRecord(topic, event.meta.id.value, event))).flatten
        }
        .liftF
        .liftFlow
        .as(AcknowledgeView(true))

  private def makeEvent(request: DeviceLocationRequest): F[Event] =
    for {
      uuid     <- Sync[F].delay(UUID.randomUUID())
      realTime <- Clock[F].realTimeInstant.map(_.toEpochMilli)
    } yield {
      val eventId    = EventId(uuid)
      val producedAt = UnixTime(realTime)

      val metadata = Metadata(eventId, producedAt)
      val event = DeviceLocationEvent(deviceId = request.deviceId,
                                      latitude = request.latitude,
                                      longitude = request.longitude,
                                      time = request.time
      )

      Event(event, metadata)
    }
}
