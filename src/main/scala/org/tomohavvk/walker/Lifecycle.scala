package org.tomohavvk.walker

import cats.Applicative
import cats.Monad
import cats.effect.Async
import cats.effect.ExitCode
import cats.effect.kernel.Spawn
import cats.effect.std.Console
import cats.implicits._
import org.tomohavvk.walker.config.AppConfig.Env.Dev
import org.tomohavvk.walker.config.AppConfig.Env.Prod

import io.odin.Logger
import org.tomohavvk.walker.config.AppConfig
import org.tomohavvk.walker.http.server.HttpServer
import org.tomohavvk.walker.module.Configs

class Lifecycle[F[_]: Async: Console](
  configs:    Configs,
  logger:     Logger[F],
  httpServer: HttpServer[F]) {

  private val startHttpServer: F[Unit] =
    for {
      _ <- httpServer.start
      _ <- logger.info("HTTP server finished")
    } yield ()

  private val devLoop: F[Unit] =
    for {
      _ <- logger.info(s"Service running in DEV. Type ENTER to stop it...")
      _ <- Monad[F].whileM_(Console[F].readLine.map(_ === "\n"))(Applicative[F].unit)
      _ <- logger.info(s"Service stopping in DEV...")
    } yield ()

  private val prodLoop: F[Unit] =
    for {
      _ <- logger.info(s"Server running in PROD. Send SIGTERM to stop it...")
      _ <- Async[F].never[Unit]
    } yield ()

  private val envLoop: AppConfig => F[Unit] = config => {
    config.env match {
      case Dev  => devLoop
      case Prod => prodLoop
    }
  }

  val start: F[ExitCode] =
    Spawn[F].race(startHttpServer, envLoop(configs.app)).as(ExitCode.Success)
}
