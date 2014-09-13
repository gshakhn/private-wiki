package com.gshakhn.privatewiki.client

import com.gshakhn.privatewiki.client.uicomponents.BinderPicker
import org.scalajs.dom

import scala.scalajs.js.annotation.JSExport
import scalatags.JsDom.all._

@JSExport
object PrivateWikiRenderer {
  @JSExport
  def render(): Unit = {
    val mainContainer = div(
      id:="mainContainer",
      cls:="container"
    ).render
    dom.document.body.appendChild(mainContainer)
    BinderPicker.addPicker(mainContainer)
  }
}
