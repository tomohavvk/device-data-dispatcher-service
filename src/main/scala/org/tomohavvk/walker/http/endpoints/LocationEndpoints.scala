package org.tomohavvk.walker.http.endpoints

import org.tomohavvk.walker.http.endpoints.bodies.LocationBodies
import org.tomohavvk.walker.http.endpoints.codecs.ErrorCodecs
import org.tomohavvk.walker.http.endpoints.codecs.LocationCodecs
import org.tomohavvk.walker.http.endpoints.mappings.ErrorMappings
import org.tomohavvk.walker.protocol.Types.TraceId
import sttp.model.StatusCode
import sttp.tapir._

class LocationEndpoints(implicit locationCodecs: LocationCodecs, val errorCodecs: ErrorCodecs)
    extends ErrorMappings
    with LocationBodies {
  import locationCodecs._

  val handleDeviceLocationEndpoint =
    apiV1Endpoint
      .in("device")
      .in("data")
      .in("location")
      .tag("location")
      .summary("Endpoint for persist device data location")
      .description("Endpoint for persist device data location")
      .post
      .in(correlationIdHeader.and(bodyForDeviceLocationRequest))
      .errorOut(oneOf(internalErrorStatusMapping, badRequestStatusMapping))
      .out(bodyForAcknowledgeView)
      .out(statusCode(StatusCode.Ok))

  private def correlationIdHeader: EndpointIO.Header[TraceId] =
    header[TraceId]("X-Trace-Id")
      .description("A unique trace ID")
      .example(TraceId("953a5959-1b0f-412a-bdab-1cbe15486a28"))

}
