package com.gshakhn.privatewiki.client.components

import japgolly.scalajs.react.{ReactComponentU, ReactComponentB}
import japgolly.scalajs.react.vdom.ReactVDom.all._

object BinderPicker {
  def apply(): ReactComponentU[Unit, Unit, Unit] = {
    val component = ReactComponentB[Unit]("BinderPicker")
      .render(
        (_) =>
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
          )).createU
    component()
  }
}
