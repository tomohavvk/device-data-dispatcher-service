package org.tomohavvk.walker.services

import org.tomohavvk.walker.protocol.error.views.AcknowledgeView
import org.tomohavvk.walker.protocol.requests.DeviceLocationRequest
import org.tomohavvk.walker.utils.ContextFlow

trait LocationService[F[_]] {
  def handleDeviceLocation(request: DeviceLocationRequest): ContextFlow[F, AcknowledgeView]
}
