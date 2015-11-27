package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.Events.UnlockBinder
import com.gshakhn.privatewiki.client.{Binder, LockedBinder, UnlockedBinder}
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react._

object BinderList {
  case class BinderListProps(binders: Seq[Binder],
                             unlockBinder: LockedBinder => UnlockBinder)
  
  private[this] val component = ReactComponentB[BinderListProps]("BinderList")
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
            unlockBinder: LockedBinder => UnlockBinder): ReactComponentU[BinderListProps, Unit, Unit, TopNode] = {
    component(BinderListProps(binders, unlockBinder))
  }
}

object LockedBinderComponent {
  case class LockedBinderProps(binder: LockedBinder,
                               unlockBinder: LockedBinder => UnlockBinder)

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
            unlockBinder: LockedBinder => UnlockBinder): ReactComponentU[LockedBinderProps, Unit, Unit, TopNode] = {
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
