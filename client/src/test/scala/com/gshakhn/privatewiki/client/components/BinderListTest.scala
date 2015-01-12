package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.{LockedBinder, UnlockedBinder}
import japgolly.scalajs.react.{React, SyntheticEvent}
import org.scalajs.dom
import org.scalajs.dom.HTMLInputElement
import org.scalajs.jquery._
import scalatags.JsDom.all._
import utest._
import utest.framework.{Test, TestSuite}
import utest.util.Tree

object BinderListTest extends TestSuite {

  val containingDiv = div(id := "containingDiv").render
  dom.document.body.appendChild(containingDiv)

  def noopUnlock: LockedBinder => (SyntheticEvent[HTMLInputElement] => Unit) = {
    b => e => Unit
  }

  def tests: Tree[Test] = TestSuite {
    "with no binders" - {
      React.render(BinderList(Seq(), noopUnlock), containingDiv)
      "unordered list" - {
        val ul = jQuery("ul")
        "should exist" - {
          assert(ul.length == 1)
        }
        "should be styled with bootstrap" - {
          assert(ul.hasClass("list-group"))
        }
        "inner list items" - {
          val li = ul.find("li")
          "should not exist" - {
            assert(li.length == 0)
          }
        }
      }
    }
    "with 1 locked binder" - {
      React.render(BinderList(Seq(LockedBinder("binder1", "")), noopUnlock), containingDiv)
      "unordered list" - {
        val ul = jQuery("ul")
        "should exist" - {
          assert(ul.length == 1)
        }
        "should be styled with bootstrap" - {
          assert(ul.hasClass("list-group"))
        }
        "inner list items" - {
          val li = ul.find("li")
          "should have 1 exist" - {
            assert(li.length == 1)
          }
          "should be styled with bootstrap" - {
            assert(li.hasClass("list-group-item"))
          }
          "should have class binder-list-item" - {
            assert(li.hasClass("binder-list-item"))
          }
          "should be marked locked" - {
            val span = li.find("span")
            assert(li.hasClass("locked-binder"))
            assert(span.hasClass("glyphicon"))
            assert(span.hasClass("glyphicon-lock"))
          }
        }
      }
    }
    "with 1 unlocked binder" - {
      React.render(BinderList(Seq(UnlockedBinder("binder1")), noopUnlock), containingDiv)
      "unordered list" - {
        val ul = jQuery("ul")
        "inner list items" - {
          val li = ul.find("li")
          "should be marked unlocked" - {
            val span = li.find("span")
            assert(li.hasClass("unlocked-binder"))
            assert(!span.hasClass("glyphicon"))
            assert(!span.hasClass("glyphicon-lock"))
          }
        }
      }
    }

  }
}
