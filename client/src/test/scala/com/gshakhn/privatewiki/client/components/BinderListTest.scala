package com.gshakhn.privatewiki.client.components

import japgolly.scalajs.react.React
import org.scalajs.dom
import org.scalajs.jquery._
import utest._
import utest.framework.TestSuite

import scalatags.JsDom.all._

object BinderListTest extends TestSuite {

  val containingDiv = div(id := "containingDiv").render
  dom.document.body.appendChild(containingDiv)

  def tests = TestSuite {
    "with no binders" - {
      React.renderComponent(BinderList(Seq()), containingDiv)
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
    "with 1 binder" - {
      React.renderComponent(BinderList(Seq("binder1")), containingDiv)
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
        }
      }
    }
  }
}
