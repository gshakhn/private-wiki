package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.UnlockedBinder
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._

object PaperList {

  case class Props(binders: Seq[AnyBinderButton])
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
  }

  private[this] val component = ReactComponentB[Props]("PaperList")
    .initialState(State(AllBinders))
    .backend(new Backend(_))
    .renderPS(
      ($, props, state) =>
        <.div(
          ^.cls := "paper-list",
          <.div(
            ^.cls := "binder-list btn-group",
            props.binders.map { btn =>
              <.div(
                ^.classSet1("btn btn-default",
                  "active" -> (state.selectedButton == btn)),
                ^.onClick --> $.backend.selectNewBinder(btn),
                btn.name
              )
            }
          )
        )).build

  def apply(binders: Seq[UnlockedBinder]): ReactComponentU[Props, State, Backend, TopNode] = {
    component(Props(Seq(AllBinders) ++ binders.map(BinderButton.apply)))
  }
}


