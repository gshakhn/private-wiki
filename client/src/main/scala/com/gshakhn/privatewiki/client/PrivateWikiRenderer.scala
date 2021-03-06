package com.gshakhn.privatewiki.client

import com.gshakhn.privatewiki.client.components.PrivateWiki
import com.gshakhn.privatewiki.shared.{AuthenticationRequest, AuthenticationResponse}
import org.scalajs.dom
import upickle.default._

import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import scalatags.JsDom.all._

@JSExportTopLevel("PrivateWikiRenderer")
object PrivateWikiRenderer {
  @JSExport
  def render(): Unit = {
    val mainDiv = div().render
    dom.document.body.appendChild(mainDiv)
    PrivateWiki(ActualClient).renderIntoDOM(mainDiv)
  }
}

object ActualClient extends Client {
  @SuppressWarnings(Array("MethodNames"))
  def authenticateBinder(request: AuthenticationRequest): Future[AuthenticationResponse] = {
    dom.ext.Ajax.post(
      url = "/authenticateBinder",
      data = write(request)
    ).map(response => read[AuthenticationResponse](response.responseText))
  }
}
