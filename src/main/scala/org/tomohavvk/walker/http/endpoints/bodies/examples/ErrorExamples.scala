package org.tomohavvk.walker.http.endpoints.bodies.examples

import org.tomohavvk.walker.protocol.error.AppError
import org.tomohavvk.walker.protocol.error.errors.BadRequestError
import org.tomohavvk.walker.protocol.error.errors.InternalError

trait ErrorExamples {

  protected val internalErrorExample: AppError = InternalError("Something went wrong")

  protected val badRequestErrorExample: AppError =
    BadRequestError("Internal error", "The request contains bad syntax or cannot be fulfilled.")

}
