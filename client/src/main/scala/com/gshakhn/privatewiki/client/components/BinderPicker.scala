package com.gshakhn.privatewiki.client.components

import japgolly.scalajs.react.{ReactComponentB, _}
import japgolly.scalajs.react.vdom.ReactVDom._
import japgolly.scalajs.react.vdom.ReactVDom.all._
import org.scalajs.dom.HTMLInputElement

object BinderPicker {
  val binderNameInputId: String = "binder-name-input"
  val binderServerPasswordId: String = "binder-password-input"

  case class BinderPickerProps(binderName: String,
                               binderPassword: String,
                               binderNameChange: SyntheticEvent[HTMLInputElement] => Unit,
                               binderPasswordChange: SyntheticEvent[HTMLInputElement] => Unit,
                               binderAdd: SyntheticEvent[HTMLInputElement] => Unit)

  private val component = ReactComponentB[BinderPickerProps]("BinderPicker")
    .render(
      props =>
        form(
          id := "binder-form",
          onsubmit ==> props.binderAdd,
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
              onchange ==> props.binderNameChange,
              value := props.binderName
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
              cls := "form-control",
              onchange ==> props.binderPasswordChange,
              value := props.binderPassword
            )
          ),
          button(
            id := "binder-button",
            tpe := "button",
            classSet("btn btn-primary",
              "disabled" -> (props.binderName.isEmpty || props.binderPassword.isEmpty)),
            onclick ==> props.binderAdd,
            "Load Binder"
          )
        )
    ).create

  def apply(binderName: String,
            binderPassword: String,
            binderNameChange: SyntheticEvent[HTMLInputElement] => Unit,
            binderPasswordChange: SyntheticEvent[HTMLInputElement] => Unit,
            binderAdd: SyntheticEvent[HTMLInputElement] => Unit): ReactComponentU[BinderPickerProps, Unit, Unit] = {
    component(BinderPickerProps(binderName, binderPassword, binderNameChange, binderPasswordChange, binderAdd))
  }
}
