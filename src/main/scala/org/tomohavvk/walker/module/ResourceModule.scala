package org.tomohavvk.walker.module

import cats.Applicative
import cats.effect.Async
import cats.effect.kernel.Resource

case class ResourcesDeps[F[_]]()

object ResourceModule {

  def make[F[_]: Async](): Resource[F, ResourcesDeps[F]] =
    Resource.make(Applicative[F].pure(ResourcesDeps[F]()))(_ => Applicative[F].unit)
}
