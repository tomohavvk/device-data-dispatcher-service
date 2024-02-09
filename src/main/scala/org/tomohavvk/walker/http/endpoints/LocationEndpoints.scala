package org.tomohavvk.walker.http.endpoints

import org.tomohavvk.walker.http.endpoints.bodies.LocationBodies
import org.tomohavvk.walker.http.endpoints.codecs.ErrorCodecs
import org.tomohavvk.walker.http.endpoints.codecs.LocationCodecs
import org.tomohavvk.walker.http.endpoints.mappings.ErrorMappings
import org.tomohavvk.walker.http.endpoints.requests.DeviceLocationRequest
import org.tomohavvk.walker.http.endpoints.schemas.EndpointSchemas
import org.tomohavvk.walker.protocol.Types.DeviceId
import org.tomohavvk.walker.protocol.Types.TraceId
import sttp.model.StatusCode
import sttp.tapir._

class LocationEndpoints(implicit locationCodecs: LocationCodecs, val errorCodecs: ErrorCodecs)
    extends ErrorMappings
    with LocationBodies {
  import locationCodecs._

  val handleDeviceLocationEndpoint =
    apiV1Endpoint
      .in("devices")
      .in(deviceIdPath.and(traceIdHeader).and(bodyForDeviceLocationList).mapTo[DeviceLocationRequest])
      .in("location")
      .tag("location")
      .summary("Endpoint for persist device data location")
      .description("Endpoint for persist device data location")
      .post
      .errorOut(oneOf(internalErrorStatusMapping, badRequestStatusMapping))
      .out(bodyForAcknowledgeView)
      .out(statusCode(StatusCode.Ok))

  private def deviceIdPath: EndpointInput.PathCapture[DeviceId] =
    path[DeviceId]
      .name("deviceId")
      .schema(EndpointSchemas.tapirDeviceIdSchema)
      .description("Device ID")
      .example(DeviceId("953a5959-1b0f-412a-bdab-1cbe15486a28"))

  private def traceIdHeader: EndpointIO.Header[TraceId] =
    header[TraceId]("X-Trace-Id")
      .description("A unique trace ID")
      .example(TraceId("953a5959-1b0f-412a-bdab-1cbe15486a28"))

}
