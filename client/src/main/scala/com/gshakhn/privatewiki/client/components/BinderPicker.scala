package com.gshakhn.privatewiki.client.components

import japgolly.scalajs.react.{ReactComponentB, _}
import japgolly.scalajs.react.vdom.ReactVDom._
import japgolly.scalajs.react.vdom.ReactVDom.all._
import org.scalajs.dom.HTMLInputElement

object BinderPicker {
  case class BinderPickerProps(newBinderName: String,
                               newBinderNameChange: SyntheticEvent[HTMLInputElement] => Unit,
                               newBinderAdd: SyntheticEvent[HTMLInputElement] => Unit)

  private val component = ReactComponentB[BinderPickerProps]("BinderPicker")
    .render(
      props =>
        form(
          id := "binder-form",
          onsubmit ==> props.newBinderAdd,
          div(
            cls := "form-group",
            div(
              cls := "input-group",
              input(
                id := "binder-input",
                tpe := "text",
                cls := "form-control",
                onchange ==> props.newBinderNameChange,
                value := props.newBinderName
              ),
              span(
                cls := "input-group-btn",
                button(
                  id := "binder-button",
                  tpe := "button",
                  cls := "btn btn-primary",
                  onclick ==> props.newBinderAdd,
                  "Load Binder"
                )
              )
            )
          )
        )).create

  def apply(newBinderName: String,
            newBinderNameChange: SyntheticEvent[HTMLInputElement] => Unit,
            newBinderAdd: SyntheticEvent[HTMLInputElement] => Unit): ReactComponentU[BinderPickerProps, Unit, Unit] = {
    component(BinderPickerProps(newBinderName, newBinderNameChange, newBinderAdd))
  }
}
