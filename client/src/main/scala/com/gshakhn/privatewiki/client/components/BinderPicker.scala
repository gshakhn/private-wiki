package com.gshakhn.privatewiki.client.components

import japgolly.scalajs.react.{ReactComponentB, _}
import japgolly.scalajs.react.vdom.ReactVDom._
import japgolly.scalajs.react.vdom.ReactVDom.all._
import org.scalajs.dom.HTMLInputElement

class BinderPicker {
  private case class BinderPickerProps(newBinderName: String,
                               onChangeHandler: SyntheticEvent[HTMLInputElement] => Unit,
                               onSubmitHandler: SyntheticEvent[HTMLInputElement] => Unit)

  private val component = ReactComponentB[BinderPickerProps]("BinderPicker")
    .render(
      props =>
        form(
          id := "binder-form",
          onsubmit ==> props.onSubmitHandler,
          div(
            cls := "form-group",
            div(
              cls := "input-group",
              input(
                id := "binder-input",
                tpe := "text",
                cls := "form-control",
                onchange ==> props.onChangeHandler,
                value := props.newBinderName
              ),
              span(
                cls := "input-group-btn",
                button(
                  id := "binder-button",
                  tpe := "button",
                  cls := "btn btn-primary",
                  onclick ==> props.onSubmitHandler,
                  "Load Binder"
                )
              )
            )
          )
        )).create

  def apply(onChangeHandler: SyntheticEvent[HTMLInputElement] => Unit,
            onSubmitHandler: SyntheticEvent[HTMLInputElement] => Unit,
            newBinderName: String): ReactComponentU[BinderPickerProps, Unit, Unit] = {
    component(BinderPickerProps(newBinderName, onChangeHandler, onSubmitHandler))
  }
}
