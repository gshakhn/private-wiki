package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.{State, Backend}
import japgolly.scalajs.react.vdom.ReactVDom.all._
import japgolly.scalajs.react.{BackendScope, ReactComponentB, ReactComponentU}

object PrivateWiki {
  def apply(backend: BackendScope[_, State] => Backend): ReactComponentU[Unit, State, Backend] = {
    val binderPicker = new BinderPicker

    val component = ReactComponentB[Unit]("PrivateWiki")
      .initialState(State(Seq(), ""))
      .backend(backend)
      .render(
        (_, S, B) => {
          div(
            id := "mainContainer",
            cls := "container",
            div(
              id := "row-1",
              cls := "row",
              div(
                id := "col-1-1",
                cls := "col-md-4",
                binderPicker(B.newBinderChange)(S.newBinderName)
              )
            ),
            div(
              id := "row-2",
              cls := "row",
              div(
                id := "col-2-1",
                cls := "col-md-4",
                BinderList(S.binderList)
              )
            )
          )}).createU
    component()
  }
}
