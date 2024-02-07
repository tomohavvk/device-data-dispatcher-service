package org.tomohavvk.walker.utils

import cats.Applicative
import cats.data.EitherT
import org.tomohavvk.walker.protocol.error.AppError

trait LiftFSyntax {

  implicit def liftFSyntax[F[_]: Applicative, A](a: F[A]): LiftFOps[F, A] =
    new LiftFOps(a)
}

final class LiftFOps[F[_]: Applicative, A](private val a: F[A]) {

  def liftF: AppFlow[F, A] =
    EitherT.liftF[F, AppError, A](a)
}
