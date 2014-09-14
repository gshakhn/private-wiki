package com.gshakhn.privatewiki.server

import akka.actor.ActorSystem
import spray.http.{MediaTypes, HttpEntity}
import spray.routing.{Route, HttpService, SimpleRoutingApp}

object Server extends App with SimpleRoutingApp with FooService {
  implicit val system = ActorSystem("my-system")

  private val defaultPort = 8080

  startServer(interface = "localhost", port = defaultPort) {
    baseRoute
  }
}

object Template {

  import scalatags.Text.all._
  import scalatags.Text.tags2.title

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
            href := "META-INF/resources/webjars/bootstrap/3.2.0/css/bootstrap.css"
          )
        ),
        body(margin := 0, onload := "PrivateWikiRenderer().render()")
      )
}

trait FooService extends HttpService {
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
