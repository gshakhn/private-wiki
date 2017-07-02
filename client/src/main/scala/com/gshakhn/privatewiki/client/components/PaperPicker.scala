package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.{Binder, BinderPaperPair, UnlockedBinder}
import com.gshakhn.privatewiki.shared.Paper
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object PaperPicker {

  case class Props(binders: Seq[UnlockedBinder], loadPaper: (BinderPaperPair => Callback))

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

    def updateSearch(e: ReactEventFromInput): Callback = {
      e.extract(_.target.value){searchText => $.modState(s => s.copy(searchText = searchText))}
    }

    def papers(props: Props, state: State): Seq[(Binder, Paper)] = {
      val papersForBinder = state.selectedButton match {
        case AllBinders => props.binders.flatMap(binder => binder.papers.map {
          (binder, _)
        })
        case BinderButton(binder) => binder.papers.map{(binder, _)}
      }
      papersForBinder.filter(_._2.name.contains(state.searchText)).toSeq
    }

    def render(props: Props, state: State): VdomElement = {
      val binderButtons: Seq[AnyBinderButton] = Seq(AllBinders) ++ props.binders.map(BinderButton.apply)
      <.div(
        ^.cls := "paper-picker",
        <.div(
          ^.cls := "binder-list btn-group",
          binderButtons.toTagMod { btn =>
            <.div(
              ^.classSet1("btn btn-default paper-picker-btn",
              "active" -> (state.selectedButton == btn)),
              VdomAttr("data-binder-name") := btn.name,
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
          papers(props, state).toTagMod{ case (binder, paper) =>
            <.div(
              ^.cls := "list-group-item paper-list-item",
              VdomAttr("data-paper-name") := paper.name,
              ^.onClick --> props.loadPaper(BinderPaperPair(binder.name, paper.name)),
              paper.name
            )
          }
        )
      )
    }
  }

  private[this] val component = ScalaComponent.builder[Props]("PaperList")
    .initialState(State(AllBinders, ""))
    .renderBackend[Backend]
    .build

  //noinspection ScalaStyle,TypeAnnotation
  def apply(binders: Seq[UnlockedBinder], loadPaper: (BinderPaperPair => Callback)) = {
    component(Props(binders, loadPaper))
  }
}
