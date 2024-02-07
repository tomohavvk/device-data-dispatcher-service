package org.tomohavvk.walker.utils

import cats.data.Kleisli

trait AppResultSyntax {

  implicit def appResultSyntax[F[_], A](appResult: AppFlow[F, A]): AppResultOps[F, A] =
    new AppResultOps(appResult)
}

final class AppResultOps[F[_], A](private val appResult: AppFlow[F, A]) extends AnyVal {
  def liftFlow: ContextFlow[F, A] = Kleisli.liftF[AppFlow[F, *], LogContext, A](appResult)
}
