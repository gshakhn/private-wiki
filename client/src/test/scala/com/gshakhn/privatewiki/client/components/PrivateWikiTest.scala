package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.{Backend, Client}
import com.gshakhn.privatewiki.shared.{AuthenticationRequest, BinderLoaded, AuthenticationResponse, WrongPassword}
import japgolly.scalajs.react.React
import japgolly.scalajs.react.test.{ChangeEventData, ReactTestUtils}
import org.scalajs.dom
import org.scalajs.jquery._
import utest._
import utest.framework.TestSuite
import scalajs.concurrent.JSExecutionContext.Implicits.runNow
import scala.concurrent.Future
import scalatags.JsDom.all._

object PrivateWikiTest extends TestSuite {

  private val containingDiv = div(id := "containingDiv").render
  dom.document.body.appendChild(containingDiv)

  def tests = TestSuite {
    "binder interaction" - {
      "should have 0 binders to start with" - reactTest { (testClient) =>
        assert(jQuery(".binder-list-item").length == 0)
      }
//      "submitting form" - {
//        "with a binder name" - {
//          "should add another list item" - reactTest { (testClient) =>
//            val input = dom.document.getElementById(BinderPicker.binderNameInputId)
//            ReactTestUtils.Simulate.change(input, ChangeEventData("new binder"))
//            val form = dom.document.getElementById("binder-form")
//            ReactTestUtils.Simulate.submit(form)
//            assert(jQuery(".binder-list-item").length == 1)
//            assert(jQuery(".binder-list-item").text() == "new binder")
//          }
//          "via button click should add another list item" - reactTest { (testClient) =>
//            val input = dom.document.getElementById(BinderPicker.binderNameInputId)
//            ReactTestUtils.Simulate.change(input, ChangeEventData("new binder"))
//            val button = dom.document.getElementById("binder-button")
//            ReactTestUtils.Simulate.click(button)
//            assert(jQuery(".binder-list-item").length == 1)
//            assert(jQuery(".binder-list-item").text() == "new binder")
//          }
//        }
//        "without a binder name" - {
//          "should not add another list item" - reactTest { (testClient) =>
//            val input = dom.document.getElementById(BinderPicker.binderNameInputId)
//            ReactTestUtils.Simulate.change(input, ChangeEventData(""))
//            val form = dom.document.getElementById("binder-form")
//            ReactTestUtils.Simulate.submit(form)
//            assert(jQuery(".binder-list-item").length == 0)
//          }
//          "via button click should not add another list item" - reactTest { (testClient) =>
//            val input = dom.document.getElementById(BinderPicker.binderNameInputId)
//            ReactTestUtils.Simulate.change(input, ChangeEventData(""))
//            val button = dom.document.getElementById("binder-button")
//            ReactTestUtils.Simulate.click(button)
//            assert(jQuery(".binder-list-item").length == 0)
//          }
//        }
//      }
      "button style" - {
        "with no binder name nor password should be disabled" - reactTest{ (testClient) =>
          val binderName = dom.document.getElementById(BinderPicker.binderNameInputId)
          ReactTestUtils.Simulate.change(binderName, ChangeEventData(""))
          val binderPassword = dom.document.getElementById(BinderPicker.binderServerPasswordId)
          ReactTestUtils.Simulate.change(binderPassword, ChangeEventData(""))
          assert(jQuery("#binder-button").hasClass("disabled"))
        }
        "with binder name but no password should be disabled" - reactTest{ (testClient) =>
          val input = dom.document.getElementById(BinderPicker.binderNameInputId)
          ReactTestUtils.Simulate.change(input, ChangeEventData("new binder"))
          val binderPassword = dom.document.getElementById(BinderPicker.binderServerPasswordId)
          ReactTestUtils.Simulate.change(binderPassword, ChangeEventData(""))
          assert(jQuery("#binder-button").hasClass("disabled"))
        }
        "with no binder name but with password should be disabled" - reactTest{ (testClient) =>
          val input = dom.document.getElementById(BinderPicker.binderNameInputId)
          ReactTestUtils.Simulate.change(input, ChangeEventData(""))
          val binderPassword = dom.document.getElementById(BinderPicker.binderServerPasswordId)
          ReactTestUtils.Simulate.change(binderPassword, ChangeEventData("secure"))
          assert(jQuery("#binder-button").hasClass("disabled"))
        }
        "with binder name and password password should be disabled" - reactTest{ (testClient) =>
          val input = dom.document.getElementById(BinderPicker.binderNameInputId)
          ReactTestUtils.Simulate.change(input, ChangeEventData("new binder"))
          val binderPassword = dom.document.getElementById(BinderPicker.binderServerPasswordId)
          ReactTestUtils.Simulate.change(binderPassword, ChangeEventData("secure"))
          assert(!jQuery("#binder-button").hasClass("disabled"))
        }
      }

      "submitting form" - {
        "for an existing binder on the server" - {
          "with the wrong password should show error on password field" - reactTest{ (testClient) =>
            testClient.response = WrongPassword
            val input = dom.document.getElementById(BinderPicker.binderNameInputId)
            ReactTestUtils.Simulate.change(input, ChangeEventData("new binder"))
            val binderPassword = dom.document.getElementById(BinderPicker.binderServerPasswordId)
            ReactTestUtils.Simulate.change(binderPassword, ChangeEventData("secure"))
            val button = dom.document.getElementById("binder-button")
            ReactTestUtils.Simulate.click(button)
            val passwordForm: JQuery = jQuery(s"#${BinderPicker.binderServerPasswordFormId}")
            assert(passwordForm.hasClass("has-error"))
            assert(testClient.requestReceived == AuthenticationRequest("new binder", "secure"))
          }
          "with the right password should add the binder to the list" - reactTest{ (testClient) =>
            testClient.response = BinderLoaded("new binder")
            val input = dom.document.getElementById(BinderPicker.binderNameInputId)
            ReactTestUtils.Simulate.change(input, ChangeEventData("new binder"))
            val binderPassword = dom.document.getElementById(BinderPicker.binderServerPasswordId)
            ReactTestUtils.Simulate.change(binderPassword, ChangeEventData("secure"))
            val button = dom.document.getElementById("binder-button")
            ReactTestUtils.Simulate.click(button)
            assert(jQuery(".binder-list-item").length == 1)
            assert(jQuery(".binder-list-item").text() == "new binder")
            assert(testClient.requestReceived == AuthenticationRequest("new binder", "secure"))
          }
        }
      }
    }
  }

  private def reactTest(x: (TestClient) => Any) = {
    val client = new TestClient()
    React.renderComponent(PrivateWiki(new Backend(_, client)), containingDiv)
    x(client)
    React.unmountComponentAtNode(containingDiv)
  }
}

class TestClient extends Client {
  var response: AuthenticationResponse = _
  var requestReceived: AuthenticationRequest = _

  def authenticateBinder(request: AuthenticationRequest): Future[AuthenticationResponse] = {
    requestReceived = request
    Future(response)
  }
}