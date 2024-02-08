package org.tomohavvk.walker.module

import cats.effect.kernel.Sync
import io.odin.Logger
import org.tomohavvk.walker.services.LocationPublisherService
import org.tomohavvk.walker.services.LocationPublisherServiceImpl
import org.tomohavvk.walker.utils.ContextFlow

object ServiceModule {

  case class ServicesDeps[F[_]](locationService: LocationPublisherService[F])

  def make[F[_]: Sync](resources: ResourcesDeps[F], logger: Logger[ContextFlow[F, *]]): ServicesDeps[F] = {
    val locationService = new LocationPublisherServiceImpl[F](resources.producer, logger)

    ServicesDeps(locationService)
  }
}
