package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.UnlockedBinder
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._

object PaperList {

  case class Props(binders: Seq[UnlockedBinder])

  private[this] val component = ReactComponentB[Props]("PaperList")
    .render_P(
      props =>
        <.div(
          ^.cls := "paper-list",
          <.div(
            ^.cls := "binder-list btn-group",
            <.div(
              ^.cls := "btn btn-default active",
              "All"
            )
          )
        )).build

  def apply(binders: Seq[UnlockedBinder]): ReactComponentU[Props, Unit, Unit, TopNode] = {
    component(Props(binders))
  }
}


