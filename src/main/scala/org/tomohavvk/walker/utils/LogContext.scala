package org.tomohavvk.walker.utils

import io.odin.loggers.HasContext
import org.tomohavvk.walker.protocol.Types.TraceId

case class LogContext(traceId: Option[TraceId] = None)

object LogContext {

  implicit val hasContext: HasContext[LogContext] = context =>
    List(
      context.traceId.map(traceId => "trace_id" -> traceId.value)
    ).flatten.toMap

  def empty: LogContext = LogContext(None)

}
