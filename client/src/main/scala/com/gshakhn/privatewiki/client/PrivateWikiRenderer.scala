package com.gshakhn.privatewiki.client

import com.gshakhn.privatewiki.client.uicomponents.PrivateWiki
import japgolly.scalajs.react.React
import org.scalajs.dom

import scala.scalajs.js.annotation.JSExport
import scalatags.JsDom.all._

@JSExport
object PrivateWikiRenderer {
  @JSExport
  def render(): Unit = {
    val mainDiv = div().render
    dom.document.body.appendChild(mainDiv)
    React.renderComponent(PrivateWiki(), mainDiv)
  }
}
