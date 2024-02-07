package org.tomohavvk.walker.http.endpoints.codecs

import org.tomohavvk.walker.http.endpoints.schemas.EndpointSchemas
import org.tomohavvk.walker.protocol.Types.DeviceId
import org.tomohavvk.walker.protocol.Types.Latitude
import org.tomohavvk.walker.protocol.Types.Longitude
import org.tomohavvk.walker.protocol.Types.TraceId
import org.tomohavvk.walker.protocol.Types.UnixTime
import org.tomohavvk.walker.protocol.error.views.AcknowledgeView
import org.tomohavvk.walker.protocol.requests.DeviceLocationRequest
import sttp.tapir.Codec.JsonCodec
import sttp.tapir.Codec.PlainCodec

case class LocationCodecs(
)(implicit val codecDeviceLocationRequest: JsonCodec[DeviceLocationRequest],
  implicit val codecAcknowledgeView:       JsonCodec[AcknowledgeView])
    extends EndpointSchemas {
  implicit val codecTraceId: PlainCodec[TraceId] = TraceId.deriving
}
