package com.gshakhn.privatewiki.client.components

import japgolly.scalajs.react.vdom.ReactVDom.all._
import japgolly.scalajs.react.{TopNode, ReactComponentB, ReactComponentU}

object BinderList {
  def apply(s: Seq[String]): ReactComponentU[Seq[String], Unit, Unit, TopNode] = {
    val component = ReactComponentB[Seq[String]]("BinderList")
      .render(
        binders =>
          ul(
            cls := "list-group",
            binders.map { binderName =>
              li(
                cls := "list-group-item binder-list-item",
                binderName
              )
            }
          )).build
    component(s)
  }
}
