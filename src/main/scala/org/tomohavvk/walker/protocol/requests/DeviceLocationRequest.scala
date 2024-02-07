package org.tomohavvk.walker.protocol.requests

import org.tomohavvk.walker.protocol.Types.DeviceId
import org.tomohavvk.walker.protocol.Types.Latitude
import org.tomohavvk.walker.protocol.Types.Longitude
import org.tomohavvk.walker.protocol.Types.UnixTime

case class DeviceLocationRequest(deviceId: DeviceId, latitude: Latitude, longitude: Longitude, time: UnixTime)
