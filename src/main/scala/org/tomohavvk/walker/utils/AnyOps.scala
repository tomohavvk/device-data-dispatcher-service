package org.tomohavvk.walker.utils

import cats.Applicative
import cats.data.EitherT
import cats.data.Kleisli
import org.tomohavvk.walker.protocol.error.AppError

trait AnySyntax {

  implicit def anySyntax[A](a: A): AnyOps[A] =
    new AnyOps(a)
}

final class AnyOps[A](private val a: A) extends AnyVal {

  def rightT[F[_]: Applicative, AA >: A]: ContextFlow[F, AA] =
    Kleisli.liftF[AppFlow[F, *], LogContext, AA](EitherT.rightT[F, AppError](a))

  def pureEitherT[F[_]: Applicative, AA >: A]: AppFlow[F, AA] =
    EitherT.rightT[F, AppError](a)
}
