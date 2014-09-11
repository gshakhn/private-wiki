package com.gshakhn.privatewiki.client

import org.scalajs.dom.HTMLDivElement

import scala.scalajs.js.annotation.JSExport
import scalatags.JsDom.all._

object BinderPicker {
  def addPicker(containingDiv: HTMLDivElement): Unit = {
    containingDiv.appendChild(
      div(
        cls := "input-group",
        input(
          tpe := "text",
          cls := "form-control"
        ).render,
        span(
          cls := "input-group-btn",
          button(
            tpe := "button",
            cls := "btn btn-primary",
            "Load Binder"
          ).render
        ).render
      ).render
    )
  }
}
