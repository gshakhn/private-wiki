package com.gshakhn.privatewiki.client

import org.scalajs.dom.HTMLDivElement

import scalatags.JsDom.all._

object BinderPicker {
  def addPicker(containingDiv: HTMLDivElement): Unit = {
    containingDiv.appendChild(
      div(
        cls := "input-group",
        input(
          tpe := "text",
          cls := "form-control"
        ),
        span(
          cls := "input-group-btn",
          button(
            tpe := "button",
            cls := "btn btn-primary",
            "Load Binder"
          )
        )
      ).render
    )
  }
}
