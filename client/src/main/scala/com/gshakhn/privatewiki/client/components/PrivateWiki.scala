package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client._
import com.gshakhn.privatewiki.shared.Paper
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import upickle.default._

object PrivateWiki {
  case class Props(client: Client)

  case class State(binderList: Seq[Binder])

  class Backend($: BackendScope[Props, State]) {
    def unlockBinder(binder: LockedBinder): Callback = {
      def replaceBinder(binderList: Seq[Binder]): Seq[Binder] = {
        binderList.map {
          case x if x == binder => UnlockedBinder(x.name, read[Set[Paper]](binder.data))
          case x => x
        }
      }

      $.modState(s => s.copy(binderList = replaceBinder(s.binderList)))
    }

    def loadBinder(lockedBinder: LockedBinder): Callback = {
      $.modState(s => s.copy(binderList = s.binderList :+ lockedBinder))
    }

    def render(props: Props, state: State): ReactElement = {
      <.div(
        ^.id := "mainContainer",
        ^.cls := "container",
        <.div(
          ^.id := "row-1",
          ^.cls := "row",
          <.div(
            ^.id := "col-1-1",
            ^.cls := "col-md-4",
            PaperPicker(state.binderList.collect { case b: UnlockedBinder => b })
          )
        ),
        <.div(
          ^.id := "row-2",
          ^.cls := "row",
          <.div(
            ^.id := "col-1-1",
            ^.cls := "col-md-4",
            BinderPicker(props.client, state.binderList, loadBinder)
          )
        ),
        <.div(
          ^.id := "row-3",
          ^.cls := "row",
          <.div(
            ^.id := "col-2-1",
            ^.cls := "col-md-4",
            BinderList(state.binderList, unlockBinder)
          )
        )
      )
    }

  }

  def apply(client: Client): ReactComponentU[Props, State, Backend, TopNode] = {
    val component = ReactComponentB[Props]("PrivateWiki")
      .initialState(State(Seq.empty))
      .renderBackend[Backend]
      .build
    component(Props(client))
  }
}
