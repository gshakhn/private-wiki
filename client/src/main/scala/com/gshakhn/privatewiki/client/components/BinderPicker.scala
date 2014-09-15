package com.gshakhn.privatewiki.client.components

import japgolly.scalajs.react.{ReactComponentB, _}
import japgolly.scalajs.react.vdom.ReactVDom._
import japgolly.scalajs.react.vdom.ReactVDom.all._
import org.scalajs.dom.HTMLInputElement

class BinderPicker {
  private var onChangeHandler: SyntheticEvent[HTMLInputElement] => Unit = _
  private var onSubmitHandler: SyntheticEvent[HTMLInputElement] => Unit = _

  private val component = ReactComponentB[String]("BinderPicker")
    .render(
      newBinderName =>
        form(
          id := "binder-form",
          onsubmit ==> onSubmitHandler,
          div(
            cls := "form-group",
            div(
              cls := "input-group",
              input(
                id := "binder-input",
                tpe := "text",
                cls := "form-control",
                onchange ==> onChangeHandler,
                value := newBinderName
              ),
              span(
                cls := "input-group-btn",
                button(
                  tpe := "button",
                  cls := "btn btn-primary",
                  "Load Binder"
                )
              )
            )
          )
        )).create

  def apply(onChangeHandler: SyntheticEvent[HTMLInputElement] => Unit,
            onSubmitHandler: SyntheticEvent[HTMLInputElement] => Unit,
            newBinderName: String): ReactComponentU[String, Unit, Unit] = {
    this.onChangeHandler = onChangeHandler
    this.onSubmitHandler = onSubmitHandler
    component(newBinderName)
  }
}
