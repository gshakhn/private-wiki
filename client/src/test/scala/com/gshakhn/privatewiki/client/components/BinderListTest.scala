package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.Events.UnlockBinder
import com.gshakhn.privatewiki.client.{Binder, LockedBinder, UnlockedBinder}
import japgolly.scalajs.react.{React, SyntheticEvent}
import org.scalajs.dom
import org.scalajs.dom.raw.HTMLInputElement
import org.scalajs.jquery._
import utest._
import utest.framework.{Test, TestSuite}
import utest.util.Tree

import scalatags.JsDom.all._

object BinderListTest extends TestSuite {

  val containingDiv = div(id := "containingDiv").render
  dom.document.body.appendChild(containingDiv)

  def noopUnlock: LockedBinder => UnlockBinder = {
    b => new UnlockBinder {
      override def apply(e: SyntheticEvent[HTMLInputElement]): Unit = {

      }
    }
  }

  def tests: Tree[Test] = TestSuite {
    "with no binders" - {
      implicit val binders: Seq[Binder] = Seq()
      "unordered list" - {
        def ul = jQuery("ul")
        "should exist" - reactTest(() => {
          assert(ul.length == 1)
        })
        "should be styled with bootstrap" - reactTest(() => {
          assert(ul.hasClass("list-group"))
        })
        "inner list items" - {
          def li = ul.find("li")
          "should not exist" - reactTest(() => {
            assert(li.length == 0)
          })
        }
      }
    }
    "with 1 locked binder" - {
      implicit val binders: Seq[Binder] = Seq(LockedBinder("binder1", ""))
      "unordered list" - {
        def ul = jQuery("ul")
        "should exist" - reactTest(() => {
          assert(ul.length == 1)
        })
        "should be styled with bootstrap" - reactTest(() => {
          assert(ul.hasClass("list-group"))
        })
        "inner list items" - {
          def li = ul.find("li")
          "should have 1 exist" - reactTest(() => {
            assert(li.length == 1)
          })
          "should be styled with bootstrap" - reactTest(() => {
            assert(li.hasClass("list-group-item"))
          })
          "should have class binder-list-item" - reactTest(() => {
            assert(li.hasClass("binder-list-item"))
          })
          "should be marked locked" - reactTest(() => {
            val span = li.find("span")
            assert(li.hasClass("locked-binder"))
            assert(span.hasClass("glyphicon"))
            assert(span.hasClass("glyphicon-lock"))
          })
        }
      }
    }
    "with 1 unlocked binder" - {
      implicit val binders: Seq[Binder] = Seq(UnlockedBinder("binder1"))
      "unordered list" - {
        def ul = jQuery("ul")
        "inner list items" - {
          def li = ul.find("li")
          "should be marked unlocked" - reactTest(() => {
            val span = li.find("span")
            assert(li.hasClass("unlocked-binder"))
            assert(!span.hasClass("glyphicon"))
            assert(!span.hasClass("glyphicon-lock"))
          })
        }
      }
    }
  }

  def reactTest(x: () => Unit)(implicit binders: Seq[Binder]): Unit = {
    React.render(BinderList(binders, noopUnlock), containingDiv)
    x()
    React.unmountComponentAtNode(containingDiv)
  }
}
