package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.{UnlockedBinder, Backend, BinderPickerData, State}
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{BackendScope, ReactComponentB, ReactComponentU, TopNode}

object PrivateWiki {
  def apply(backend: BackendScope[_, State] => Backend): ReactComponentU[Unit, State, Backend, TopNode] = {
    val component = ReactComponentB[Unit]("PrivateWiki")
      .initialState(State(Seq(), BinderPickerData("", "", wrongPassword = false)))
      .backend(backend)
      .renderS(
        ($, S) => {
          <.div(
            ^.id := "mainContainer",
            ^.cls := "container",
            <.div(
              ^.id := "row-1",
              ^.cls := "row",
              <.div(
                ^.id := "col-1-1",
                ^.cls := "col-md-4",
                PaperPicker(S.binderList.collect { case b: UnlockedBinder => b })
              )
            ),
            <.div(
              ^.id := "row-2",
              ^.cls := "row",
              <.div(
                ^.id := "col-1-1",
                ^.cls := "col-md-4",
                BinderPicker(S.binderPickerData, $.backend.newBinderNameChange, $.backend.newBinderPasswordChange, $.backend.newBinderAdd)
              )
            ),
            <.div(
              ^.id := "row-3",
              ^.cls := "row",
              <.div(
                ^.id := "col-2-1",
                ^.cls := "col-md-4",
                BinderList(S.binderList, $.backend.unlockBinder)
              )
            )
          )}).buildU
    component()
  }
}
