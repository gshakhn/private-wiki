package com.gshakhn.privatewiki.client

import org.scalajs.jquery._
import utest._
import utest.framework.TestSuite

object PrivateWikiRendererTest extends TestSuite {

  PrivateWikiRenderer.render()
  
  def tests = TestSuite {
    "container div"- {
      val mainContainer = jQuery("div#mainContainer")
      "should exist"- {
        assert(mainContainer.length == 1)
      }
      "should be styled with bootstrap"- {
        assert(mainContainer.hasClass("container"))
      }
      "first row"- {
        val firstRow = mainContainer.find("div#row-1")
        "should exist"- {
          assert(firstRow.length == 1)
        }
        "should be styled with bootstrap"- {
          assert(firstRow.hasClass("row"))
        }
        "first column"- {
          val firstCol = mainContainer.find("div#col-1-1")
          "should exist"- {
            assert(firstCol.length == 1)
          }
          "should be styled with bootstrap"- {
            assert(firstCol.hasClass("col-md-4"))
          }
        }
      }
    }
  }
}
