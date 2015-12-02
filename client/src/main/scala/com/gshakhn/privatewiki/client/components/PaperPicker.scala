package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.UnlockedBinder
import com.gshakhn.privatewiki.shared.Paper
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._

object PaperPicker {

  case class Props(binders: Seq[UnlockedBinder])
  case class State(selectedButton: AnyBinderButton)

  trait AnyBinderButton {
    def name: String
  }

  case object AllBinders extends AnyBinderButton {
    override def name: String = "All"
  }

  case class BinderButton(binder: UnlockedBinder) extends AnyBinderButton {
    override def name: String = binder.name
  }

  class Backend($: BackendScope[Props, State]) {
    def selectNewBinder(newSelectedButton: AnyBinderButton): Callback = {
      $.modState(s => s.copy(selectedButton = newSelectedButton))
    }

    def papers(props: Props, state: State): Seq[Paper] = {
      state.selectedButton match {
        case AllBinders => props.binders.flatMap(_.papers)
        case BinderButton(binder) => binder.papers.toSeq
      }
    }

    def render(props: Props, state: State): ReactElement = {
      val binderButtons = Seq(AllBinders) ++ props.binders.map(BinderButton.apply)
      <.div(
        ^.cls := "paper-picker",
        <.div(
          ^.cls := "binder-list btn-group",
          binderButtons.map { btn =>
            <.div(
              ^.classSet1("btn btn-default paper-picker-btn",
                "active" -> (state.selectedButton == btn)),
              ^.onClick --> selectNewBinder(btn),
              btn.name
            )
          }
        ),
        <.div(
          ^.cls := "paper-list list-group",
          papers(props, state).map{ paper =>
            <.div(
              ^.cls := "list-group-item paper-list-item",
              paper.name
            )
          }
        )
      )
    }
  }

  private[this] val component = ReactComponentB[Props]("PaperList")
    .initialState(State(AllBinders))
    .renderBackend[Backend]
    .build

  def apply(binders: Seq[UnlockedBinder]): ReactComponentU[Props, State, Backend, TopNode] = {
    component(Props(binders))
  }
}