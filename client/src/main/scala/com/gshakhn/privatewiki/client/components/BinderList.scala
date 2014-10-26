package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.{UnlockedBinder, LockedBinder, Binder}
import japgolly.scalajs.react.vdom.ReactVDom._
import japgolly.scalajs.react.vdom.ReactVDom.all._
import japgolly.scalajs.react.{SyntheticEvent, TopNode, ReactComponentB, ReactComponentU}
import org.scalajs.dom.HTMLInputElement

object BinderList {
  case class BinderListProps(binders: Seq[Binder],
                             unlockBinder: LockedBinder => (SyntheticEvent[HTMLInputElement] => Unit))
  
  private val component = ReactComponentB[BinderListProps]("BinderList")
    .render(
      props =>
        ul(
          cls := "list-group",
          props.binders.map {
            case b: LockedBinder => LockedBinderComponent(b, props.unlockBinder)
            case b: UnlockedBinder => UnlockedBinderComponent(b)
          }
        )).build
  
  def apply(binders: Seq[Binder],
            unlockBinder: LockedBinder => (SyntheticEvent[HTMLInputElement] => Unit)): ReactComponentU[BinderListProps, Unit, Unit, TopNode] = {
    component(BinderListProps(binders, unlockBinder))
  }
}

object LockedBinderComponent {
  case class LockedBinderProps(binder: LockedBinder,
                               unlockBinder: LockedBinder => (SyntheticEvent[HTMLInputElement] => Unit))

  private val component = ReactComponentB[LockedBinderProps]("LockedBinder")
    .render(
      props =>
        li(
          cls := "list-group-item binder-list-item locked-binder",
          props.binder.name,
          onclick ==> props.unlockBinder(props.binder),
          span(
            cls := "glyphicon glyphicon-lock pull-right"
          )
        )
    ).build
  
  def apply(binder: LockedBinder,
            unlockBinder: LockedBinder => (SyntheticEvent[HTMLInputElement] => Unit)): ReactComponentU[LockedBinderProps, Unit, Unit, TopNode] = {
    component(LockedBinderProps(binder, unlockBinder))
  }
}

object UnlockedBinderComponent {
  def apply(b: UnlockedBinder): ReactComponentU[UnlockedBinder, Unit, Unit, TopNode] = {
    val component = ReactComponentB[UnlockedBinder]("UnlockedBinder")
      .render(
        binder =>
          li(
            cls := "list-group-item binder-list-item unlocked-binder",
            binder.name
          )
      ).build
    component(b)
  }
}
