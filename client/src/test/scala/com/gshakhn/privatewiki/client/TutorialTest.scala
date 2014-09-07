package com.gshakhn.privatewiki.client

import utest._
import org.scalajs.jquery.jQuery

object TutorialTest extends TestSuite {

  // Initialize App
  ScalaJSExample.main()

  def tests = TestSuite {
    'HelloWorld {
      assert(jQuery("#foo").text == "File Browser")
    }
  }
}