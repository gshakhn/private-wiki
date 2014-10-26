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
      "loading binder" - {
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
          "with the right password should add the binder to the list and clear input" - reactTest{ (testClient) =>
            testClient.response = BinderLoaded("new binder", "")
            enterBinderName("new binder")
            enterBinderPassword("secure")
            clickLoadBinder()
            assertBinderList("new binder")
            assertNoBinderName()
            assertNoPassword()
            assertAuthenticationRequest(testClient, AuthenticationRequest("new binder", "secure"))
          }
        }
      }
      "unlocking binder that has been loaded" - {
        "clicking the locked binder should unlock it" - reactTest{ (testClient) =>
          testClient.response = BinderLoaded("new binder", "")
          enterBinderName("new binder")
          enterBinderPassword("secure")
          clickLoadBinder()
          clickUnlockBinder(0)
          assertUnlockedBinder(0)
        }
      }
    }
  }
}
