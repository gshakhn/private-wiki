package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.shared.Paper
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object PaperDisplay {

  case class Props(paper: Paper)

  @SuppressWarnings(Array("UnusedMethodParameter"))
  class Backend($: BackendScope[Props, Unit]) {
    def render(props: Props): VdomElement = {
      <.div(
        ^.cls := "barfoo",
        <.div(
          ^.cls := "foobar",
          props.paper.text
        )
      )
    }
  }

  private[this] val component = ScalaComponent.builder[Props]("PaperList")
    .renderBackend[Backend]
    .build

  //noinspection ScalaStyle,TypeAnnotation
  def apply(paper: Paper) = {
    component(Props(paper))
  }
}
