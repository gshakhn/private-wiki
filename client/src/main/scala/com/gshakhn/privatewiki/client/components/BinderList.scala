package com.gshakhn.privatewiki.client.components

import japgolly.scalajs.react.vdom.ReactVDom.all._
import japgolly.scalajs.react.{ReactComponentB, ReactComponentU}

object BinderList {
  def apply(s: Seq[String]): ReactComponentU[Seq[String], Unit, Unit] = {
    val component = ReactComponentB[Seq[String]]("BinderList")
      .render(
        binders =>
          ul(
            cls := "list-group",
            binders.map{ binderName =>
              li(
                cls:="list-group-item",
                binderName
              )
            }
          )).create
    component(s)
  }

}
