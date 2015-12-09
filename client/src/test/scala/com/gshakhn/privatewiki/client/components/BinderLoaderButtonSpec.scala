package com.gshakhn.privatewiki.client.components

import com.gshakhn.privatewiki.client.components.testutil.{PrivateWikiBaseSpec, PageInteractions}
import PageInteractions._
import org.scalajs.jquery._
import org.scalatest.path

class BinderLoaderButtonSpec extends PrivateWikiBaseSpec {

  override def newInstance: path.FunSpecLike = new BinderLoaderButtonSpec

  describe("A PrivateWiki with a BinderLoader component") {
    implicit val client = new TestClient
    render

    describe("with no binder name") {
      enterBinderName("")

      describe("and no password") {
        enterBinderPassword("")

        it("should disable the button") {
          jQuery("#binder-button") should haveClass("disabled")
        }
      }

      describe("and a password") {
        enterBinderPassword("secure")

        it("should disable the button") {
          jQuery("#binder-button") should haveClass("disabled")
        }
      }
    }

    describe("with a binder name") {
      enterBinderName("new binder")

      describe("and no password") {
        enterBinderPassword("")

        it("should disable the button") {
          jQuery("#binder-button") should haveClass("disabled")
        }
      }

      describe("and a password") {
        enterBinderPassword("secure")

        it("should enable the button") {
          jQuery("#binder-button") shouldNot haveClass("disabled")
        }
      }
    }
  }

  tearDown()
}
