package org.tomohavvk.walker.http.endpoints.bodies

import org.tomohavvk.walker.http.endpoints.bodies.examples.LocationExamples
import org.tomohavvk.walker.protocol.error.views.AcknowledgeView
import org.tomohavvk.walker.protocol.requests.DeviceLocationRequest
import sttp.tapir.Codec.JsonCodec
import sttp.tapir.EndpointIO.Body
import sttp.tapir.customCodecJsonBody

trait LocationBodies extends LocationExamples {

  protected def bodyForDeviceLocationRequest(
    implicit codec: JsonCodec[DeviceLocationRequest]
  ): Body[String, DeviceLocationRequest] =
    customCodecJsonBody[DeviceLocationRequest].example(exampleDeviceLocationRequest)

  protected def bodyForAcknowledgeView(
    implicit codec: JsonCodec[AcknowledgeView]
  ): Body[String, AcknowledgeView] =
    customCodecJsonBody[AcknowledgeView].example(exampleAcknowledgeView)
}
