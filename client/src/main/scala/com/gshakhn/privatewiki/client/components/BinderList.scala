package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.{Binder, LockedBinder, UnlockedBinder}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._

object BinderList {
  case class Props(binders: Seq[Binder],
                             unlockBinder: LockedBinder => Callback)
  
  private[this] val component = ReactComponentB[Props]("BinderList")
    .render_P(
      props =>
        <.ul(
          ^.cls := "list-group",
          props.binders.map {
            case b: LockedBinder => LockedBinderComponent(b, props.unlockBinder)
            case b: UnlockedBinder => UnlockedBinderComponent(b)
          }
        )).build
  
  def apply(binders: Seq[Binder],
            unlockBinder: LockedBinder => Callback): ReactComponentU[Props, Unit, Unit, TopNode] = {
    component(Props(binders, unlockBinder))
  }
}

object LockedBinderComponent {
  case class LockedBinderProps(binder: LockedBinder,
                               unlockBinder: LockedBinder => Callback)

  private[this] val component = ReactComponentB[LockedBinderProps]("LockedBinder")
    .render_P(
      props => {
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
    ).build
  
  def apply(binder: LockedBinder,
            unlockBinder: LockedBinder => Callback): ReactComponentU[LockedBinderProps, Unit, Unit, TopNode] = {
    component(LockedBinderProps(binder, unlockBinder))
  }
}

object UnlockedBinderComponent {
  def apply(b: UnlockedBinder): ReactComponentU[UnlockedBinder, Unit, Unit, TopNode] = {
    val component = ReactComponentB[UnlockedBinder]("UnlockedBinder")
      .render_P(
        binder =>
          <.li(
            ^.cls := "list-group-item binder-list-item unlocked-binder",
            binder.name,
            "data-binder-name".reactAttr := binder.name
          )
      ).build
    component(b)
  }
}
