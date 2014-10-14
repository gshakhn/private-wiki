package com.gshakhn.privatewiki.server

import akka.actor.ActorSystem
import com.gshakhn.privatewiki.server.services.StaticService
import spray.routing.SimpleRoutingApp

object Server extends App with SimpleRoutingApp with StaticService {
  implicit val system = ActorSystem("my-system")

  private val defaultPort = 8080

  startServer(interface = "localhost", port = defaultPort) {
    baseRoute
  }
}

