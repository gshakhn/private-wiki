package com.gshakhn.privatewiki.client

import com.gshakhn.privatewiki.client.components.PrivateWiki
import com.gshakhn.privatewiki.shared.{AuthenticationRequest, AuthenticationResponse}
import japgolly.scalajs.react.React
import org.scalajs.dom
import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow
import scala.scalajs.js.annotation.JSExport
import scalatags.JsDom.all._

@JSExport("PrivateWikiRenderer")
object PrivateWikiRenderer {
  @JSExport
  def render(): Unit = {
    val mainDiv = div().render
    dom.document.body.appendChild(mainDiv)
    React.render(PrivateWiki(new Backend(_, ActualClient)), mainDiv)
  }
}

object ActualClient extends Client {
  def authenticateBinder(request: AuthenticationRequest): Future[AuthenticationResponse] = {
    dom.extensions.Ajax.post(
      url = "/authenticateBinder",
      data = upickle.write(request)
    ).map(response => upickle.read[AuthenticationResponse](response.responseText))
  }
}
