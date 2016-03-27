package com.gshakhn.privatewiki.server.services

import scalatags.Text.all._
import scalatags.Text.tags2.title
import spray.http.{HttpEntity, MediaTypes}
import spray.routing._

trait StaticService extends HttpService {
  def baseRoute: Route = get {
    pathSingleSlash {
      complete {
        HttpEntity(
          MediaTypes.`text/html`,
          Template.txt
        )
      }
    } ~
      getFromResourceDirectory("")
  }
}

object Template {

  val txt =
    "<!DOCTYPE html>" +
      html(
        head(
          title("Example Scala.js application"),
          meta(
            charset := "utf-8"
          ),
          meta(
            httpEquiv := "X-UA-Compatible",
            content := "IE=edge"
          ),
          meta(
            name := "viewport",
            content := "width=device-width, initial-scale=1"
          ),
          script(`type` := "text/javascript", src := "/client-jsdeps.js"),
          script(`type` := "text/javascript", src := "/client-fastopt.js"),
          link(
            rel := "stylesheet",
            `type` := "text/css",
            href := "META-INF/resources/webjars/bootstrap/3.3.6/css/bootstrap.css"
          ),
          link(
            rel := "stylesheet",
            `type` := "text/css",
            href := "META-INF/resources/webjars/bootstrap-markdown/2.9.0/css/bootstrap-markdown.min.css"
          )
        ),
        body(margin := 0, onload := "PrivateWikiRenderer().render()")
      )
}
