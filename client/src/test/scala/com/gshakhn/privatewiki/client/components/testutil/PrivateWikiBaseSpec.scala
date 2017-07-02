package com.gshakhn.privatewiki.client.components.testutil

import com.gshakhn.privatewiki.client.Client
import com.gshakhn.privatewiki.client.components.PrivateWiki
import com.gshakhn.privatewiki.client.components.PrivateWiki.{Backend, Props, State}
import com.gshakhn.privatewiki.client.components.testutil.RunNowExecutionContext.runNow
import com.gshakhn.privatewiki.shared.{AuthenticationRequest, AuthenticationResponse}
import japgolly.scalajs.react.component.Scala
import japgolly.scalajs.react.internal.Effect.Id

import scala.concurrent.Future

trait PrivateWikiBaseSpec extends ReactJsBaseSpec {
  var rootComponent: Scala.MountedSimple[Id, Props, State, Backend] = _

  class TestClient extends Client {
    var response: AuthenticationResponse = _
    var requestReceived: Option[AuthenticationRequest] = None

    def authenticateBinder(request: AuthenticationRequest): Future[AuthenticationResponse] = {
      requestReceived = Some(request)
      Future(response)
    }
  }

  def render(implicit client: TestClient): Unit = {
    rootComponent = PrivateWiki(client).renderIntoDOM(containingDiv)
  }
}
