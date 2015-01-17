package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.BinderPickerData
import com.gshakhn.privatewiki.client.Events.{BinderAdd, BinderPasswordChange, BinderNameChange}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom.HTMLInputElement

object BinderPicker {
  val binderNameInputId: String = "binder-name-input"
  val binderServerPasswordId: String = "binder-password-input"
  val binderServerPasswordFormId: String = "binder-password-form"
  case class BinderPickerProps(data: BinderPickerData,
                               binderNameChange: BinderNameChange,
                               binderPasswordChange: BinderPasswordChange,
                               binderAdd: BinderAdd)

  private[this] val component = ReactComponentB[BinderPickerProps]("BinderPicker")
    .render(
      props =>
        <.form(
          ^.id := "binder-form",
          ^.onSubmit ==> props.binderAdd,
          <.div(
            ^.cls := "form-group",
            <.label(
              ^.htmlFor := binderNameInputId,
              ^.cls := "control-label",
              "Binder Name"
            ),
            <.input(
              ^.id := binderNameInputId,
              ^.tpe := "text",
              ^.cls := "form-control",
              ^.onChange ==> props.binderNameChange,
              ^.value := props.data.binderName
            )
          ),
          <.div(
            ^.id := binderServerPasswordFormId,
            ^.classSet1("form-group",
              "has-error" -> props.data.wrongPassword
            ),
            <.label(
              ^.htmlFor := binderServerPasswordId,
              ^.cls := "control-label",
              "Binder Password"
            ),
            <.input(
              ^.id := binderServerPasswordId,
              ^.tpe := "password",
              ^.cls := "form-control",
              ^.onChange ==> props.binderPasswordChange,
              ^.value := props.data.binderPassword
            )
          ),
          <.button(
            ^.id := "binder-button",
            ^.tpe := "submit",
            ^.classSet1("btn btn-primary",
              "disabled" -> (!props.data.hasData)),
            ^.onClick ==> props.binderAdd,
            "Load Binder"
          )
        )
    ).build

  def apply(data: BinderPickerData,
            binderNameChange: BinderNameChange,
            binderPasswordChange: BinderPasswordChange,
            binderAdd: BinderAdd): ReactComponentU[BinderPickerProps, Unit, Unit, TopNode] = {
    component(BinderPickerProps(data, binderNameChange, binderPasswordChange, binderAdd))
  }
}
