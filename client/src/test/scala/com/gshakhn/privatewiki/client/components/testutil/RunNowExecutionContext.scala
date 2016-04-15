package com.gshakhn.privatewiki.client.components.testutil

import scala.concurrent.ExecutionContextExecutor

object RunNowExecutionContext
  extends ExecutionContextExecutor {

  implicit val runNow: ExecutionContextExecutor = this

  def execute(runnable: Runnable): Unit = {
    try {
      runnable.run()
    } catch {
      case t: Throwable => reportFailure(t)
    }
  }

  def reportFailure(t: Throwable): Unit =
    t.printStackTrace()

}
