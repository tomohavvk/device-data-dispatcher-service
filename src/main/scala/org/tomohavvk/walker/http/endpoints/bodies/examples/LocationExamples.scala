package org.tomohavvk.walker.http.endpoints.bodies.examples

import org.tomohavvk.walker.protocol.Types.DeviceId
import org.tomohavvk.walker.protocol.Types.Latitude
import org.tomohavvk.walker.protocol.Types.Longitude
import org.tomohavvk.walker.protocol.Types.UnixTime
import org.tomohavvk.walker.protocol.error.views.AcknowledgeView
import org.tomohavvk.walker.protocol.requests.DeviceLocationRequest

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.UUID

trait LocationExamples {

  protected val exampleDeviceLocationRequest: DeviceLocationRequest =
    DeviceLocationRequest(
      deviceId = DeviceId(UUID.randomUUID().toString),
      latitude = Latitude(48),
      longitude = Longitude(38),
      time = UnixTime(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
    )

  protected val exampleAcknowledgeView: AcknowledgeView =
    AcknowledgeView()
}
