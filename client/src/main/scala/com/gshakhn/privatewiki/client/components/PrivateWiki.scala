package com.gshakhn.privatewiki.client.components

import japgolly.scalajs.react.vdom.ReactVDom.all._
import japgolly.scalajs.react.{ReactComponentB, ReactComponentU}

object PrivateWiki {
  def apply(): ReactComponentU[Unit, Unit, Unit] = {
    val component = ReactComponentB[Unit]("PrivateWiki")
      .render(
        (_) =>
          div(
            id := "mainContainer",
            cls := "container",
            div(
              id := "row-1",
              cls := "row",
              div(
                id := "col-1-1",
                cls := "col-md-4",
                BinderPicker()
              )
            ),div(
                id := "row-2",
                cls := "row",
                div(
                  id := "col-2-1",
                  cls := "col-md-4",
                  BinderList(Seq("test1", "test2"))
                )
              ))).createU
    component()
  }
}
