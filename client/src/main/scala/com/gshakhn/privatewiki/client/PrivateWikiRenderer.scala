package com.gshakhn.privatewiki.client

import org.scalajs.dom.HTMLDivElement

import scala.scalajs.js.annotation.JSExport
import scalatags.JsDom.all._
import org.scalajs.dom

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
