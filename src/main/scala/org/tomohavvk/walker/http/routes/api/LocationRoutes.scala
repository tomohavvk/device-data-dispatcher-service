package org.tomohavvk.walker.http.routes.api

import cats.data.Kleisli
import cats.effect.kernel.Async
import cats.implicits.catsSyntaxOptionId
import io.odin.Logger
import org.http4s.HttpRoutes
import org.tomohavvk.walker.http.endpoints.LocationEndpoints
import org.tomohavvk.walker.http.routes.MappedHttp4sHttpEndpoint
import org.tomohavvk.walker.services.LocationPublisherService
import org.tomohavvk.walker.utils.ContextFlow
import org.tomohavvk.walker.utils.LogContext
import org.tomohavvk.walker.utils.liftFSyntax
import sttp.tapir.server.http4s.Http4sServerOptions

class LocationRoutes[F[_]: Async](
  endpoints:              LocationEndpoints,
  service:                LocationPublisherService[F]
)(implicit serverOptions: Http4sServerOptions[F],
  logger:                 Logger[ContextFlow[F, *]]) {

  private val handleDeviceLocationRoute: HttpRoutes[F] =
    endpoints.handleDeviceLocationEndpoint.toRoutes(
      {
        case (_, request) => service.publish(request)
      },
      {
        case (traceId, request) => LogContext(traceId.some, request.deviceId.some)
      }
    )

  val routes: HttpRoutes[F] = handleDeviceLocationRoute

}
