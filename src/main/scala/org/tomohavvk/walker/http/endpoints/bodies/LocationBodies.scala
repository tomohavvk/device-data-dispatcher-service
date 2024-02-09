package org.tomohavvk.walker.http.endpoints.bodies

import org.tomohavvk.walker.http.endpoints.bodies.examples.LocationExamples
import org.tomohavvk.walker.protocol.DeviceLocation
import org.tomohavvk.walker.protocol.error.views.AcknowledgeView
import sttp.tapir.Codec.JsonCodec
import sttp.tapir.EndpointIO.Body
import sttp.tapir.customCodecJsonBody

trait LocationBodies extends LocationExamples {

  protected def bodyForDeviceLocationList(
    implicit codec: JsonCodec[List[DeviceLocation]]
  ): Body[String, List[DeviceLocation]] =
    customCodecJsonBody[List[DeviceLocation]].example(exampleDeviceLocationList)

  protected def bodyForAcknowledgeView(
    implicit codec: JsonCodec[AcknowledgeView]
  ): Body[String, AcknowledgeView] =
    customCodecJsonBody[AcknowledgeView].example(exampleAcknowledgeView)
}
