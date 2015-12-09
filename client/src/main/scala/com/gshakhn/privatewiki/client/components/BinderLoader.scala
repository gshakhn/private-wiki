package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.{Binder, Client, LockedBinder}
import com.gshakhn.privatewiki.shared.{AuthenticationRequest, BinderLoaded, WrongPassword}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow

object BinderLoader {
  val binderNameInputId: String = "binder-name-input"
  val binderServerPasswordId: String = "binder-password-input"
  val binderServerPasswordFormId: String = "binder-password-form"

  case class Props(client: Client, currentlyLoadedBinders: Seq[Binder], loadBinderCallback: LockedBinder => Callback)

  case class State(binderName: String, binderPassword: String, wrongPassword: Boolean) {
    def hasData: Boolean = !binderName.isEmpty && !binderPassword.isEmpty
  }

  class Backend($: BackendScope[Props, State]) {
    def updateBinderName(e: ReactEventI): Callback = {
      $.modState(s => s.copy(binderName = e.target.value))
    }

    def updateBinderPassword(e: ReactEventI): Callback = {
      $.modState(s => s.copy(binderPassword = e.target.value))
    }

    def loadBinder(e: ReactEventI): Callback = {
      e.preventDefaultCB >>
        $.state.zip($.props).flatMap { case (state, props) =>
          if (state.hasData) {
            if (props.currentlyLoadedBinders.map(_.name).contains(state.binderName)) {
              $.modState(s => State("", "", wrongPassword = false))
            } else {
              Callback.future {
                props.client.authenticateBinder(AuthenticationRequest(state.binderName, state.binderPassword)).map {
                  case WrongPassword =>
                    $.modState(s => s.copy(wrongPassword = true))
                  case BinderLoaded(binderName, encryptionType, binderData) =>
                    $.modState(s => State("", "", wrongPassword = false)) >>
                      props.loadBinderCallback(LockedBinder(binderName, encryptionType, binderData))
                }
              }
            }
          } else {
            Callback.empty
          }
        }
    }


    def render(state: State): ReactElement = {
      <.form(
        ^.id := "binder-form",
        ^.onSubmit ==> loadBinder,
        <.div(
          ^.cls := "form-group",
          <.label(
            ^.htmlFor := binderNameInputId,
            ^.cls := "control-label",
            "Binder Name"
          ),
          <.input.text(
            ^.id := binderNameInputId,
            ^.cls := "form-control",
            ^.onChange ==> updateBinderName,
            ^.value := state.binderName
          )
        ),
        <.div(
          ^.id := binderServerPasswordFormId,
          ^.classSet1("form-group",
            "has-error" -> state.wrongPassword
          ),
          <.label(
            ^.htmlFor := binderServerPasswordId,
            ^.cls := "control-label",
            "Binder Password"
          ),
          <.input.password(
            ^.id := binderServerPasswordId,
            ^.cls := "form-control",
            ^.onChange ==> updateBinderPassword,
            ^.value := state.binderPassword
          )
        ),
        <.button(
          ^.id := "binder-button",
          ^.tpe := "submit",
          ^.classSet1("btn btn-primary",
            "disabled" -> (!state.hasData)),
          ^.onClick ==> loadBinder,
          "Load Binder"
        )
      )
    }
  }


  private[this] val component = ReactComponentB[Props]("BinderLoader")
    .initialState(State("", "", wrongPassword = false))
    .renderBackend[Backend]
    .build

  def apply(client: Client,
            currentlyLoadedBinders: Seq[Binder],
            loadBinderCallback: LockedBinder => Callback): ReactComponentU[Props, State, Backend, TopNode] = {
    component(Props(client, currentlyLoadedBinders, loadBinderCallback))
  }
}
