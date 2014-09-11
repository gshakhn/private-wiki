package com.gshakhn.privatewiki.client

import org.scalajs.dom
import org.scalajs.jquery._
import utest._
import utest.framework.TestSuite

import scalatags.JsDom.all._

object PrivateWikiRendererTest extends TestSuite {

  PrivateWikiRenderer.render()
  
  def tests = TestSuite {
    "container div"- {
      "should exist"- {
        assert(jQuery("div#mainContainer").length == 1)
      }
      "should be styled with bootstrap"- {
        assert(jQuery("div#mainContainer").hasClass("container"))
      }
    }
  }
}
