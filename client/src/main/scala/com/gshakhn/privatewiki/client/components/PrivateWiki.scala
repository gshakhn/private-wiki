package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client._
import com.gshakhn.privatewiki.shared.Paper
import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Js
import japgolly.scalajs.react.component.Scala.MountedImpure
import japgolly.scalajs.react.vdom.html_<^._
import upickle.default._

import scala.concurrent.ExecutionContext

object PrivateWiki {
  case class Props(client: Client)

  case class State(loadedBinders: Seq[Binder], loadedPapers: Seq[BinderPaperPair])

  class Backend($: BackendScope[Props, State])(implicit executionContext: ExecutionContext) {
    def unlockBinder(binder: LockedBinder): Callback = {
      @SuppressWarnings(Array("MethodNames"))
      def replaceBinder(binderList: Seq[Binder]): Seq[Binder] = {
        binderList.map {
          case x if x == binder => UnlockedBinder(x.name, read[Set[Paper]](binder.data))
          case x => x
        }
      }

      $.modState(s => s.copy(loadedBinders = replaceBinder(s.loadedBinders)))
    }

    def loadPaper(binderPaperPair: BinderPaperPair): Callback = {
      $.modState(s => s.copy(loadedPapers = s.loadedPapers :+ binderPaperPair))
    }

    def loadBinder(lockedBinder: LockedBinder): Callback = {
      $.modState(s => s.copy(loadedBinders = s.loadedBinders :+ lockedBinder))
    }

    def render(props: Props, state: State): VdomElement = {
      <.div(
        ^.id := "mainContainer",
        ^.cls := "container",
        <.div(
          ^.id := "row-1",
          ^.cls := "row",
          <.div(
            ^.id := "col-1-1",
            ^.cls := "col-md-4",
            PaperPicker(state.loadedBinders.collect { case b: UnlockedBinder => b }, loadPaper)
          )
        ),
        <.div(
          ^.id := "row-2",
          ^.cls := "row",
          <.div(
            ^.id := "col-1-1",
            ^.cls := "col-md-4",
            BinderLoader(props.client, state.loadedBinders, loadBinder)
          )
        ),
        <.div(
          ^.id := "row-3",
          ^.cls := "row",
          <.div(
            ^.id := "col-2-1",
            ^.cls := "col-md-4",
            BinderList(state.loadedBinders, unlockBinder)
          )
        ),
        PaperDisplay(Paper("some paper", "some text"))
      )
    }

  }

  def apply(client: Client)
           (implicit executionContext: ExecutionContext): Js.UnmountedSimple[Props, MountedImpure[Props, State, Backend]] = {
    val component = ScalaComponent.builder[Props]("PrivateWiki")
      .initialState(State(Seq.empty, Seq.empty))
      .renderBackend[Backend]
      .build
    component(Props(client))
  }
}
