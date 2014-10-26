package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.{Client, Backend}
import com.gshakhn.privatewiki.shared.{AuthenticationResponse, AuthenticationRequest}
import japgolly.scalajs.react.React
import japgolly.scalajs.react.test.{ChangeEventData, ReactTestUtils}
import org.scalajs.dom
import org.scalajs.jquery._
import utest._
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow
import scala.concurrent.Future
import scalatags.JsDom.all._

trait TestHelpers {

  private val containingDiv = div(id := "containingDiv").render
  dom.document.body.appendChild(containingDiv)

  def assertEnabledButton(): Unit = {
    assert(!jQuery("#binder-button").hasClass("disabled"))
  }

  def assertDisabledButton(): Unit = {
    assert(jQuery("#binder-button").hasClass("disabled"))
  }

  def assertPasswordError(): Unit = {
    val passwordForm = jQuery(s"#${BinderPicker.binderServerPasswordFormId}")
    assert(passwordForm.hasClass("has-error"))
  }

  def assertNoBinderName(): Unit = {
    val binderName = jQuery(s"#${BinderPicker.binderNameInputId}").value().toString
    assert(binderName == "")
  }

  def assertNoPassword(): Unit = {
    val password = jQuery(s"#${BinderPicker.binderServerPasswordId}").value().toString
    assert(password == "")
  }

  def assertAuthenticationRequest(testClient: TestClient, expectedRequest: AuthenticationRequest): Unit = {
    assert(testClient.requestReceived == expectedRequest)
  }

  def assertBinderList(binderNames: String*): Unit = {
    val listItems = jQuery(".binder-list-item")
    val actualLength: Int = listItems.length
    val expectedLength: Int = binderNames.length
    assert(actualLength == expectedLength)
    binderNames.zipWithIndex.foreach{case (expectedName, index) =>
      val actualText: String = listItems.eq(index).text()
      assert(actualText == expectedName)
    }
  }

  def assertUnlockedBinder(binderIndex: Int): Unit = {
    val li = jQuery(".binder-list-item").eq(binderIndex)
    assert(li.hasClass("unlocked-binder"))
  }

  def clickLoadBinder(): Unit = {
    val button = dom.document.getElementById("binder-button")
    ReactTestUtils.Simulate.click(button)
  }

  def clickUnlockBinder(binderIndex: Int): Unit = {
    val li = dom.document.getElementsByClassName("binder-list-item")(0)
    ReactTestUtils.Simulate.click(li)
  }

  def enterBinderPassword(password: String): Unit = {
    val binderPasswordNode = dom.document.getElementById(BinderPicker.binderServerPasswordId)
    ReactTestUtils.Simulate.change(binderPasswordNode, ChangeEventData(password))
  }

  def enterBinderName(name: String): Unit = {
    val binderNameNode = dom.document.getElementById(BinderPicker.binderNameInputId)
    ReactTestUtils.Simulate.change(binderNameNode, ChangeEventData(name))
  }

  def reactTest(x: (TestClient) => Any) = {
    val client = new TestClient()
    React.renderComponent(PrivateWiki(new Backend(_, client)), containingDiv)
    x(client)
    React.unmountComponentAtNode(containingDiv)
  }

  class TestClient extends Client {
    var response: AuthenticationResponse = _
    var requestReceived: AuthenticationRequest = _

    def authenticateBinder(request: AuthenticationRequest): Future[AuthenticationResponse] = {
      requestReceived = request
      Future(response)
    }
  }
}
