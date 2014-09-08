package com.gshakhn.privatewiki.server

import spray.testkit.{TestFrameworkInterface, Specs2Interface, RouteTest}
import utest.{TestSuite, asserts}

abstract class uTestRouteTest extends TestSuite with RouteTest with uTestInterface

trait uTestInterface extends TestFrameworkInterface {
  override def failTest(msg: String): Nothing = asserts.assertError(msg, Seq())
}
