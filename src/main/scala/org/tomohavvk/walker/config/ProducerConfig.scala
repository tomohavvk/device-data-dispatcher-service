package org.tomohavvk.walker.config

import fs2.kafka.Acks
import org.tomohavvk.walker.config.ProducerConfig.RetryConfig
import org.tomohavvk.walker.config.ProducerConfig.SchemaRegistryConfig

import scala.concurrent.duration.FiniteDuration

case class ProducerConfig(
  topic:            String,
  bootstrapServers: String,
  ack:              Acks,
  requestTimeout:   FiniteDuration,
  closeTimeout:     FiniteDuration,
  retry:            RetryConfig,
  schemaRegistry:   SchemaRegistryConfig)

object ProducerConfig {

  case class RetryConfig(attempts: Int, interval: FiniteDuration)

  case class SchemaRegistryConfig(endpoint: String)

}
