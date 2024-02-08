package org.tomohavvk.walker

import fs2.kafka.KafkaProducer

case class EventProducer[F[_], K, E](topic: String, producer: KafkaProducer[F, K, E])
