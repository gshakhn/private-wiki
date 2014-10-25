package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.shared.{AuthenticationRequest, BinderLoaded, WrongPassword}
import utest._
import utest.framework.{Test, TestSuite}
import utest.util.Tree

object PrivateWikiTest extends TestSuite with TestHelpers {

  def tests: Tree[Test] = TestSuite {
    "binder interaction" - {
      "should have 0 binders to start with" - reactTest { (testClient) =>
        assertBinderList()
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
        "with no binder name nor password should be disabled" - reactTest{ (_) =>
          enterBinderName("")
          enterBinderPassword("")
          assertDisabledButton()
        }
        "with binder name but no password should be disabled" - reactTest{ (_) =>
          enterBinderName("new binder")
          enterBinderPassword("")
          assertDisabledButton()
        }
        "with no binder name but with password should be disabled" - reactTest{ (_) =>
          enterBinderName("")
          enterBinderPassword("secure")
          assertDisabledButton()
        }
        "with binder name and password password should be disabled" - reactTest{ (_) =>
          enterBinderName("new binder")
          enterBinderPassword("secure")
          assertEnabledButton()
        }
      }

      "submitting form" - {
        "for an existing binder on the server" - {
          "with the wrong password should show error on password field" - reactTest{ (testClient) =>
            testClient.response = WrongPassword
            enterBinderName("new binder")
            enterBinderPassword("secure")
            clickLoadBinder()
            assertBinderList()
            assertPasswordError()
            assertAuthenticationRequest(testClient, AuthenticationRequest("new binder", "secure"))
          }
          "with the right password should add the binder to the list" - reactTest{ (testClient) =>
            testClient.response = BinderLoaded("new binder")
            enterBinderName("new binder")
            enterBinderPassword("secure")
            clickLoadBinder()
            assertBinderList("new binder")
            assertAuthenticationRequest(testClient, AuthenticationRequest("new binder", "secure"))
          }
        }
      }
    }
  }
}
