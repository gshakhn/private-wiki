package com.gshakhn.privatewiki.client

import org.scalajs.dom
import org.scalajs.jquery._
import utest._
import utest.framework.TestSuite

import scalatags.JsDom.all._

object BinderPickerTest extends TestSuite {

  val containingDiv = div(id:="containingDiv").render
  dom.document.body.appendChild(containingDiv)
  
  BinderPicker.addPicker(containingDiv)
  
  def tests = TestSuite {
    "text box"- {
      "should exist"- {
        assert(jQuery(containingDiv).find(":text").length == 1)
      }
    }
    "button"- {
      "should exist"- {
        assert(jQuery(containingDiv).find(":button").length == 1)
      }
      "should have type button"- {
        assert(jQuery(containingDiv).find(":button").attr("type") == "button")
      }
      "should say 'Load Binder'"- {
        assert(jQuery(containingDiv).find(":button").text() == "Load Binder")
      }
      "should be styled with bootstrap"- {
        assert(jQuery(containingDiv).find(":button").hasClass("btn"))
        assert(jQuery(containingDiv).find(":button").hasClass("btn-primary"))
      }
    }
  }
}
