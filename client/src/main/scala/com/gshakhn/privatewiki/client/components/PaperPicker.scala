package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.UnlockedBinder
import com.gshakhn.privatewiki.shared.Paper
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._

object PaperPicker {

  case class Props(binders: Seq[UnlockedBinder])

  case class State(selectedButton: AnyBinderButton, searchText: String)

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

    def updateSearch(e: ReactEventI): Callback = {
      $.modState(s => s.copy(searchText = e.target.value))
    }

    def papers(props: Props, state: State): Seq[Paper] = {
      val papersForBinder = state.selectedButton match {
        case AllBinders => props.binders.flatMap(_.papers)
        case BinderButton(binder) => binder.papers.toSeq
      }
      papersForBinder.filter(_.name.contains(state.searchText))
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
              "data-binder-name".reactAttr := btn.name,
              ^.onClick --> selectNewBinder(btn),
              btn.name
            )
          }
        ),
      <.div(
        ^.cls := "form-group has-feedback",
        <.input.text(
          ^.id := "paper-picker-search",
          ^.cls := "form-control",
          ^.onChange ==> updateSearch,
          ^.value := state.searchText
        ),
        <.span(
          ^.cls := "glyphicon glyphicon-search form-control-feedback"
        )
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
    .initialState(State(AllBinders, ""))
    .renderBackend[Backend]
    .build

  def apply(binders: Seq[UnlockedBinder]): ReactComponentU[Props, State, Backend, TopNode] = {
    component(Props(binders))
  }
}
