package org.tomohavvk.walker.services

import org.tomohavvk.walker.protocol.DeviceLocation
import org.tomohavvk.walker.protocol.Types.DeviceId
import org.tomohavvk.walker.protocol.error.views.AcknowledgeView
import org.tomohavvk.walker.utils.ContextFlow

trait LocationPublisherService[F[_]] {
  def publish(deviceId: DeviceId, locations: List[DeviceLocation]): ContextFlow[F, AcknowledgeView]
}
