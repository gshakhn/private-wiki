package com.gshakhn.privatewiki.client

import com.gshakhn.privatewiki.client.uicomponents.BinderPicker
import japgolly.scalajs.react.React
import org.scalajs.dom

import scala.scalajs.js.annotation.JSExport
import scalatags.JsDom.all._

@JSExport
object PrivateWikiRenderer {
  @JSExport
  def render(): Unit = {
    val col_1_1 = div(
      id := "col-1-1",
      cls := "col-md-4"
    ).render
    val mainContainer = div(
      id := "mainContainer",
      cls := "container",
      div(
        id := "row-1",
        cls := "row",
        col_1_1
      )
    ).render
    dom.document.body.appendChild(mainContainer)
    React.renderComponent(BinderPicker(), col_1_1)
  }
}
