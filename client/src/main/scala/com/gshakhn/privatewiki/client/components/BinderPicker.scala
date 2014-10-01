package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.BinderPickerData
import japgolly.scalajs.react.{ReactComponentB, _}
import japgolly.scalajs.react.vdom.ReactVDom._
import japgolly.scalajs.react.vdom.ReactVDom.all._
import org.scalajs.dom.HTMLInputElement

object BinderPicker {
  val binderNameInputId: String = "binder-name-input"
  val binderServerPasswordId: String = "binder-password-input"
  val binderServerPasswordFormId: String = "binder-password-form"

  case class BinderPickerProps(data: BinderPickerData,
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
              "htmlFor".attr := binderNameInputId,
              "Binder Name"
            ),
            input(
              id := binderNameInputId,
              tpe := "text",
              cls := "form-control",
              onchange ==> props.binderNameChange,
              value := props.data.binderName
            )
          ),
          div(
            id := binderServerPasswordFormId,
            classSet("form-group",
              "has-error" -> props.data.wrongPassword
            ),
            label(
              "htmlFor".attr := binderServerPasswordId,
              "Binder Password"
            ),
            input(
              id := binderServerPasswordId,
              tpe := "password",
              cls := "form-control",
              onchange ==> props.binderPasswordChange,
              value := props.data.binderPassword
            )
          ),
          button(
            id := "binder-button",
            tpe := "button",
            classSet("btn btn-primary",
              "disabled" -> (props.data.binderName.isEmpty || props.data.binderPassword.isEmpty)),
            onclick ==> props.binderAdd,
            "Load Binder"
          )
        )
    ).create

  def apply(data: BinderPickerData,
            binderNameChange: SyntheticEvent[HTMLInputElement] => Unit,
            binderPasswordChange: SyntheticEvent[HTMLInputElement] => Unit,
            binderAdd: SyntheticEvent[HTMLInputElement] => Unit): ReactComponentU[BinderPickerProps, Unit, Unit] = {
    component(BinderPickerProps(data, binderNameChange, binderPasswordChange, binderAdd))
  }
}
