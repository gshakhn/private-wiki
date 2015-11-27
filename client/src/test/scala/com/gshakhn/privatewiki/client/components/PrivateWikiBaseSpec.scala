package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.{Backend, Client}
import com.gshakhn.privatewiki.shared.{AuthenticationRequest, AuthenticationResponse}
import japgolly.scalajs.react.ReactDOM

import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow

trait PrivateWikiBaseSpec extends ReactJsBaseSpec {
  class TestClient extends Client {
    var response: AuthenticationResponse = _
    var requestReceived: Option[AuthenticationRequest] = None

    def authenticateBinder(request: AuthenticationRequest): Future[AuthenticationResponse] = {
      requestReceived = Some(request)
      Future(response)
    }
  }

  def render(implicit client: TestClient): Unit = {
    ReactDOM.render(PrivateWiki(new Backend(_, client)), containingDiv)
  }
}
