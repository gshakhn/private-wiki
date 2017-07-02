package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.{Binder, LockedBinder, UnlockedBinder}
import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.vdom.html_<^._

object BinderList {
  case class Props(binders: Seq[Binder],
                             unlockBinder: LockedBinder => Callback)

  @SuppressWarnings(Array("UnusedMethodParameter"))
  class Backend($: BackendScope[Props, Unit]) {
    def render(props: Props): VdomElement = {
      <.ul(
        ^.cls := "list-group",
        props.binders.toTagMod {
          case b: LockedBinder => LockedBinderComponent(b, props.unlockBinder)
          case b: UnlockedBinder => UnlockedBinderComponent(b)
        }

      )
    }
  }

  private[this] val component = ScalaComponent.builder[Props]("BinderList")
    .renderBackend[Backend]
    .build

  def apply(binders: Seq[Binder],
            unlockBinder: LockedBinder => Callback): Unmounted[Props, Unit, Backend] = {
    component(Props(binders, unlockBinder))
  }
}

object LockedBinderComponent {
  case class Props(binder: LockedBinder,
                   unlockBinder: LockedBinder => Callback)

  @SuppressWarnings(Array("UnusedMethodParameter"))
  class Backend($: BackendScope[Props, Unit]) {
    def render(props: Props): VdomElement = {
      <.li(
        ^.cls := "list-group-item binder-list-item locked-binder",
        props.binder.name,
        VdomAttr("data-binder-name") := props.binder.name,
        ^.onClick --> props.unlockBinder(props.binder),
        <.span(
          ^.cls := "glyphicon glyphicon-lock pull-right"
        )
      )
    }
  }

  private[this] val component = ScalaComponent.builder[Props]("LockedBinder")
    .renderBackend[Backend]
    .build

  //noinspection ScalaStyle,TypeAnnotation
  def apply(binder: LockedBinder,
            unlockBinder: LockedBinder => Callback) = {
    component(Props(binder, unlockBinder))
  }

}

object UnlockedBinderComponent {
  type Props = UnlockedBinder

  @SuppressWarnings(Array("UnusedMethodParameter"))
  class Backend($: BackendScope[Props, Unit]) {
    def render(props: Props): VdomElement = {
      <.li(
        ^.cls := "list-group-item binder-list-item unlocked-binder",
        props.name,
        VdomAttr("data-binder-name") := props.name
      )
    }
  }

  private[this] val component = ScalaComponent.builder[Props]("UnlockedBinder")
    .renderBackend[Backend]
    .build

  //noinspection ScalaStyle,TypeAnnotation
  def apply(unlockedBinder: UnlockedBinder) = {
    component(unlockedBinder)
  }
}
