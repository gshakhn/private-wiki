package com.gshakhn.privatewiki.server

import akka.actor.ActorSystem
import spray.routing.SimpleRoutingApp

object Server extends App with SimpleRoutingApp {
  implicit val system = ActorSystem("my-system")

  startServer(interface = "localhost", port = 8080) {
    path("hello") {
      get {
        complete {
          <h1>Say hello to spray</h1>
        }
      }
    }
  }

  private val defaultPort = 8080
}
