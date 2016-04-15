package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.shared.Paper
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._

import scala.scalajs.js

object PaperDisplay {

  case class Props(paper: Paper)

  @SuppressWarnings(Array("UnusedMethodParameter"))
  class Backend($: BackendScope[Props, Unit]) {
    def render(props: Props): ReactElement = {
      <.div(
        ^.cls := "barfoo",
        <.div(
          ^.cls := "foobar",
          props.paper.text
        )
      )
    }
  }

  private[this] val component = ReactComponentB[Props]("PaperList")
    .renderBackend[Backend]
    .componentDidMount(scope => Callback {
      js.Dynamic.global.jQuery(scope.getDOMNode()).find(".foobar").markdown()
    })
    .build

  def apply(paper: Paper): ReactComponentU[Props, Unit, Backend, TopNode] = {
    component(Props(paper))
  }
}
