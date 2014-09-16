package com.gshakhn.privatewiki.client.components

import japgolly.scalajs.react.{ReactComponentB, _}
import japgolly.scalajs.react.vdom.ReactVDom._
import japgolly.scalajs.react.vdom.ReactVDom.all._
import org.scalajs.dom.HTMLInputElement

object BinderPicker {
  val binderNameInputId: String = "binder-name-input"
  val binderServerPasswordId: String = "binder-password-input"

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
            label(
              `for` := binderNameInputId,
              "Binder Name"
            ),
            input(
              id := binderNameInputId,
              tpe := "text",
              cls := "form-control",
              onchange ==> props.newBinderNameChange,
              value := props.newBinderName
            )
          ),
          div(
            cls := "form-group",
            label(
              `for` := binderServerPasswordId,
              "Binder Password"
            ),
            input(
              id := binderServerPasswordId,
              tpe := "password",
              cls := "form-control"
//              onchange ==> props.newBinderNameChange,
//              value := props.newBinderName
            )
          ),
          button(
            id := "binder-button",
            tpe := "button",
            classSet("btn btn-primary",
              "disabled" -> props.newBinderName.isEmpty),
            onclick ==> props.newBinderAdd,
            "Load Binder"
          )
        )
    ).create

  def apply(newBinderName: String,
            newBinderNameChange: SyntheticEvent[HTMLInputElement] => Unit,
            newBinderAdd: SyntheticEvent[HTMLInputElement] => Unit): ReactComponentU[BinderPickerProps, Unit, Unit] = {
    component(BinderPickerProps(newBinderName, newBinderNameChange, newBinderAdd))
  }
}
