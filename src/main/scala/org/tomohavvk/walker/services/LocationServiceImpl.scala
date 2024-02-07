package org.tomohavvk.walker.services

import cats.Applicative
import cats.implicits.toFunctorOps
import io.odin.Logger
import org.tomohavvk.walker.protocol.error.views.AcknowledgeView
import org.tomohavvk.walker.protocol.requests.DeviceLocationRequest
import org.tomohavvk.walker.utils.ContextFlow

class LocationServiceImpl[F[_]: Applicative](logger: Logger[ContextFlow[F, *]]) extends LocationService[F] {

  def handleDeviceLocation(request: DeviceLocationRequest): ContextFlow[F, AcknowledgeView] =
    logger
      .info(request.toString)
      .as(AcknowledgeView(true))

}
