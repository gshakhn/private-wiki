package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.{BinderPickerData, State, Backend}
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{TopNode, BackendScope, ReactComponentB, ReactComponentU}

object PrivateWiki {
  def apply(backend: BackendScope[_, State] => Backend): ReactComponentU[Unit, State, Backend, TopNode] = {
    val component = ReactComponentB[Unit]("PrivateWiki")
      .initialState(State(Seq(), BinderPickerData("", "", wrongPassword = false)))
      .backend(backend)
      .render(
        (_, S, B) => {
          <.div(
            ^.id := "mainContainer",
            ^.cls := "container",
            <.div(
              ^.id := "row-1",
              ^.cls := "row",
              <.div(
                ^.id := "col-1-1",
                ^.cls := "col-md-4",
                BinderPicker(S.binderPickerData, B.newBinderNameChange, B.newBinderPasswordChange, B.newBinderAdd)
              )
            ),
            <.div(
              ^.id := "row-2",
              ^.cls := "row",
              <.div(
                ^.id := "col-2-1",
                ^.cls := "col-md-4",
                BinderList(S.binderList, B.unlockBinder)
              )
            )
          )}).buildU
    component()
  }
}
