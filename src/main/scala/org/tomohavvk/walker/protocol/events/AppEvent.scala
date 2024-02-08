package org.tomohavvk.walker.protocol.events

import org.tomohavvk.walker.protocol.Types.DeviceId
import org.tomohavvk.walker.protocol.Types.EventId
import org.tomohavvk.walker.protocol.Types.Latitude
import org.tomohavvk.walker.protocol.Types.Longitude
import org.tomohavvk.walker.protocol.Types.UnixTime

sealed trait AppEvent

case class Metadata(id: EventId, producedAt: UnixTime)

case class DeviceLocationEvent(deviceId: DeviceId, latitude: Latitude, longitude: Longitude, time: UnixTime)
    extends AppEvent

case class Event(event: AppEvent, meta: Metadata)
