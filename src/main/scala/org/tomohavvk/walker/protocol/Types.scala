package org.tomohavvk.walker.protocol

import io.estatico.newtype.macros.newtype

import java.time.LocalDateTime

object Types {
  @newtype case class DeviceId(value: String)
  @newtype case class Latitude(value: Double)
  @newtype case class Longitude(value: Double)
  @newtype case class UnixTime(value: Long)

  @newtype case class TraceId(value: String)
  @newtype case class ApiErrorMessage(value: String)
  @newtype case class ErrorCode(value: String)
  @newtype case class HttpCode(value: Int)
  @newtype case class LogErrorMessage(value: String)
}
