package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.{LockedBinder, Binder}
import japgolly.scalajs.react.vdom.ReactVDom.all._
import japgolly.scalajs.react.{TopNode, ReactComponentB, ReactComponentU}

object BinderList {
  def apply(s: Seq[Binder]): ReactComponentU[Seq[Binder], Unit, Unit, TopNode] = {
    val component = ReactComponentB[Seq[Binder]]("BinderList")
      .render(
        binders =>
          ul(
            cls := "list-group",
            binders.map {
              case lb: LockedBinder => LockedBinderComponent(lb)
            }
          )).build
    component(s)
  }
}

object LockedBinderComponent {
  def apply(b: LockedBinder): ReactComponentU[LockedBinder, Unit, Unit, TopNode] = {
    val component = ReactComponentB[LockedBinder]("LockedBinder")
      .render(
          binder =>
              li(
                cls := "list-group-item binder-list-item",
                binder.name,
                span(
                  classSet("glyphicon glyphicon-lock pull-right" -> binder.locked)
                )
              )
          ).build
    component(b)
  }
}
