package org.tomohavvk.walker.http.endpoints.schemas

import org.tomohavvk.walker.protocol.Types._
import org.tomohavvk.walker.protocol.error.AppError
import sttp.tapir.Schema._
import sttp.tapir.SchemaType.SProductField
import sttp.tapir.FieldName
import sttp.tapir.Schema
import sttp.tapir.SchemaType
import sttp.tapir.generic.Configuration

trait EndpointSchemas {

  implicit val tapirSchemasConfig: Configuration = Configuration.default.withSnakeCaseMemberNames

  implicit def schemaForError[T <: AppError]: Schema[T] =
    Schema(
      SchemaType.SProduct(
        List(
          SProductField(FieldName("message"), schemaForString, err => Some(err.apiMessage.value)),
          SProductField(FieldName("code"), schemaForString, err => Some(err.code.value))
        )
      )
    )

  implicit val tapirDeviceIdSchema: Schema[DeviceId]                 = DeviceId.deriving
  implicit val tapirLatitudeSchema: Schema[Latitude]                 = Latitude.deriving
  implicit val tapirLongitudeSchema: Schema[Longitude]               = Longitude.deriving
  implicit val tapirUnixTimeSchema: Schema[UnixTime]                 = UnixTime.deriving
  implicit val tapirAccuracySchema: Schema[Accuracy]                 = Accuracy.deriving
  implicit val tapirAltitudeSchema: Schema[Altitude]                 = Altitude.deriving
  implicit val tapirSpeedSchema: Schema[Speed]                       = Speed.deriving
  implicit val tapirBearingSchema: Schema[Bearing]                   = Bearing.deriving
  implicit val tapirAltitudeAccuracySchema: Schema[AltitudeAccuracy] = AltitudeAccuracy.deriving
}

object EndpointSchemas extends EndpointSchemas
