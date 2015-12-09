package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.{Binder, LockedBinder, UnlockedBinder}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._

object BinderList {
  case class Props(binders: Seq[Binder],
                             unlockBinder: LockedBinder => Callback)

  class Backend($: BackendScope[Props, Unit]) {
    def render(props: Props): ReactElement = {
      <.ul(
        ^.cls := "list-group",
        props.binders.map {
          case b: LockedBinder => LockedBinderComponent(b, props.unlockBinder)
          case b: UnlockedBinder => UnlockedBinderComponent(b)
        }
      )
    }
  }

  private[this] val component = ReactComponentB[Props]("BinderList")
    .renderBackend[Backend]
    .build

  def apply(binders: Seq[Binder],
            unlockBinder: LockedBinder => Callback): ReactComponentU[Props, Unit, Backend, TopNode] = {
    component(Props(binders, unlockBinder))
  }
}

object LockedBinderComponent {
  case class Props(binder: LockedBinder,
                   unlockBinder: LockedBinder => Callback)

  class Backend($: BackendScope[Props, Unit]) {
    def render(props: Props): ReactElement = {
      <.li(
        ^.cls := "list-group-item binder-list-item locked-binder",
        props.binder.name,
        "data-binder-name".reactAttr := props.binder.name,
        ^.onClick --> props.unlockBinder(props.binder),
        <.span(
          ^.cls := "glyphicon glyphicon-lock pull-right"
        )
      )
    }
  }

  private[this] val component = ReactComponentB[Props]("LockedBinder")
    .renderBackend[Backend]
    .build

  def apply(binder: LockedBinder,
            unlockBinder: LockedBinder => Callback): ReactComponentU[Props, Unit, Backend, TopNode] = {
    component(Props(binder, unlockBinder))
  }

}

object UnlockedBinderComponent {
  type Props = UnlockedBinder

  class Backend($: BackendScope[Props, Unit]) {
    def render(props: Props): ReactElement = {
      <.li(
        ^.cls := "list-group-item binder-list-item unlocked-binder",
        props.name,
        "data-binder-name".reactAttr := props.name
      )
    }
  }

  private[this] val component = ReactComponentB[Props]("UnlockedBinder")
    .renderBackend[Backend]
    .build

  def apply(unlockedBinder: UnlockedBinder): ReactComponentU[Props, Unit, Backend, TopNode] = {
    component(unlockedBinder)
  }
}
