package org.tomohavvk.walker.module

import cats.effect.kernel.Sync
import cats.implicits.toFunctorOps
import com.typesafe.config.ConfigFactory
import fs2.kafka.Acks
import org.tomohavvk.walker.config.AppConfig
import org.tomohavvk.walker.config.ProducerConfig
import org.tomohavvk.walker.config.ServerConfig
import pureconfig.ConfigReader
import pureconfig.ConfigSource
import pureconfig.error.CannotConvert
import pureconfig.generic.auto._
import pureconfig.module.enumeratum._
import pureconfig.module.catseffect.syntax.CatsEffectConfigSource

case class Configs(
  app:                        AppConfig,
  server:                     ServerConfig,
  deviceGeoDataEventProducer: ProducerConfig)

object Configs {

  implicit val acksReader: ConfigReader[Acks] = ConfigReader[String].emap {
    case "0"   => Right(Acks.Zero)
    case "1"   => Right(Acks.One)
    case "all" => Right(Acks.All)
    case other => Left(CannotConvert(other, "acks", "It's not supported"))
  }

  def make[F[_]](implicit F: Sync[F]): F[Right[Nothing, Configs]] =
    ConfigSource
      .fromConfig(ConfigFactory.load())
      .loadF[F, Configs]
      .map(Right(_))
}
