package org.tomohavvk.walker.module

import cats.effect.Async
import cats.effect.kernel.Resource
import vulcan.Codec
import fs2.kafka._
import fs2.kafka.vulcan.AvroSettings
import fs2.kafka.vulcan.SchemaRegistryClientSettings
import fs2.kafka.vulcan.avroSerializer
import org.tomohavvk.walker.EventProducer
import org.tomohavvk.walker.config.ProducerConfig
import org.tomohavvk.walker.protocol.Types.Key
import org.tomohavvk.walker.protocol.events.Event
import org.tomohavvk.walker.serialization.avro.EventCodecs

import java.nio.charset.StandardCharsets

case class ResourcesDeps[F[_]](producer: EventProducer[F, Key, Event])

object ResourceModule extends EventCodecs {

  def make[F[_]: Async](config: ProducerConfig): Resource[F, ResourcesDeps[F]] =
    for {
      producer <- makeProducerResource[F, Event](config)
    } yield ResourcesDeps[F](EventProducer(config.topic, producer))

  private def makeProducerResource[F[_]: Async, V: Codec](
    producerConfig: ProducerConfig
  ): Resource[F, KafkaProducer[F, Key, V]] = {
    implicit val keySerializer: Serializer[F, Key] = Serializer.uuid[F](StandardCharsets.UTF_8)

    implicit val valueSerializer: Resource[F, ValueSerializer[F, V]] =
      avroSerializer[V].forValue(AvroSettings(SchemaRegistryClientSettings[F](producerConfig.schemaRegistry.endpoint)))

    val producerSettings =
      ProducerSettings[F, Key, V]
        .withBootstrapServers(producerConfig.bootstrapServers)
        .withProperty("retry.backoff.ms", producerConfig.retry.interval.toMillis.toString)

    KafkaProducer.resource(producerSettings)
  }

}
