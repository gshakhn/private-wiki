package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.Backend
import japgolly.scalajs.react.React
import japgolly.scalajs.react.test.{ChangeEventData, ReactTestUtils}
import org.scalajs.dom
import utest.framework.TestSuite
import utest._
import org.scalajs.jquery._

import scalatags.JsDom.all._

object PrivateWikiTest extends TestSuite {

  private val containingDiv = div(id := "containingDiv").render
  dom.document.body.appendChild(containingDiv)

  def tests = TestSuite {
    "binder interaction" - {
      "should have 0 binders to start with" - reactTest { () =>
        assert(jQuery(".binder-list-item").length == 0)
      }
//      "submitting form" - {
//        "with a binder name" - {
//          "should add another list item" - reactTest { () =>
//            val input = dom.document.getElementById(BinderPicker.binderNameInputId)
//            ReactTestUtils.Simulate.change(input, ChangeEventData("new binder"))
//            val form = dom.document.getElementById("binder-form")
//            ReactTestUtils.Simulate.submit(form)
//            assert(jQuery(".binder-list-item").length == 1)
//            assert(jQuery(".binder-list-item").text() == "new binder")
//          }
//          "via button click should add another list item" - reactTest { () =>
//            val input = dom.document.getElementById(BinderPicker.binderNameInputId)
//            ReactTestUtils.Simulate.change(input, ChangeEventData("new binder"))
//            val button = dom.document.getElementById("binder-button")
//            ReactTestUtils.Simulate.click(button)
//            assert(jQuery(".binder-list-item").length == 1)
//            assert(jQuery(".binder-list-item").text() == "new binder")
//          }
//        }
//        "without a binder name" - {
//          "should not add another list item" - reactTest { () =>
//            val input = dom.document.getElementById(BinderPicker.binderNameInputId)
//            ReactTestUtils.Simulate.change(input, ChangeEventData(""))
//            val form = dom.document.getElementById("binder-form")
//            ReactTestUtils.Simulate.submit(form)
//            assert(jQuery(".binder-list-item").length == 0)
//          }
//          "via button click should not add another list item" - reactTest { () =>
//            val input = dom.document.getElementById(BinderPicker.binderNameInputId)
//            ReactTestUtils.Simulate.change(input, ChangeEventData(""))
//            val button = dom.document.getElementById("binder-button")
//            ReactTestUtils.Simulate.click(button)
//            assert(jQuery(".binder-list-item").length == 0)
//          }
//        }
//      }
      "button style" - {
        "with no binder name nor password should be disabled" - reactTest{ () =>
          val binderName = dom.document.getElementById(BinderPicker.binderNameInputId)
          ReactTestUtils.Simulate.change(binderName, ChangeEventData(""))
          val binderPassword = dom.document.getElementById(BinderPicker.binderServerPasswordId)
          ReactTestUtils.Simulate.change(binderPassword, ChangeEventData(""))
          assert(jQuery("#binder-button").hasClass("disabled"))
        }
        "with binder name but no password should be disabled" - reactTest{ () =>
          val input = dom.document.getElementById(BinderPicker.binderNameInputId)
          ReactTestUtils.Simulate.change(input, ChangeEventData("new binder"))
          val binderPassword = dom.document.getElementById(BinderPicker.binderServerPasswordId)
          ReactTestUtils.Simulate.change(binderPassword, ChangeEventData(""))
          assert(jQuery("#binder-button").hasClass("disabled"))
        }
        "with no binder name but with password should be disabled" - reactTest{ () =>
          val input = dom.document.getElementById(BinderPicker.binderNameInputId)
          ReactTestUtils.Simulate.change(input, ChangeEventData(""))
          val binderPassword = dom.document.getElementById(BinderPicker.binderServerPasswordId)
          ReactTestUtils.Simulate.change(binderPassword, ChangeEventData("secure"))
          assert(jQuery("#binder-button").hasClass("disabled"))
        }
        "with binder name and password password should be disabled" - reactTest{ () =>
          val input = dom.document.getElementById(BinderPicker.binderNameInputId)
          ReactTestUtils.Simulate.change(input, ChangeEventData("new binder"))
          val binderPassword = dom.document.getElementById(BinderPicker.binderServerPasswordId)
          ReactTestUtils.Simulate.change(binderPassword, ChangeEventData("secure"))
          assert(!jQuery("#binder-button").hasClass("disabled"))
        }
      }
    }
  }

  private def reactTest(x: () => Any) = {
    React.renderComponent(PrivateWiki(new Backend(_)), containingDiv)
    x()
    React.unmountComponentAtNode(containingDiv)
  }
}
