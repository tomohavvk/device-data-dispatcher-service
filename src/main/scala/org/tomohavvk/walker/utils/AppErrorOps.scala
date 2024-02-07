package org.tomohavvk.walker.utils

import cats.Applicative
import cats.data.EitherT
import cats.data.Kleisli
import org.tomohavvk.walker.protocol.error.AppError

trait AppErrorSyntax {

  implicit def appErrorSyntax(appError: AppError): AppErrorOps =
    new AppErrorOps(appError)
}

final class AppErrorOps(private val appError: AppError) extends AnyVal {

  def leftT[F[_]: Applicative, A]: ContextFlow[F, A] =
    Kleisli.liftF[AppFlow[F, *], LogContext, A](EitherT.leftT[F, A](appError))
}
