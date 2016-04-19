package com.gshakhn.privatewiki.server

import akka.actor.ActorSystem
import com.gshakhn.privatewiki.server.interactors.FakeAuthenticationInteractorComponent
import com.gshakhn.privatewiki.server.services.{AuthenticationService, StaticService}
import spray.routing.SimpleRoutingApp

object Server extends App with SimpleRoutingApp with StaticService with AuthenticationService with FakeAuthenticationInteractorComponent {
  implicit val system = ActorSystem("my-system")

  private[this] val defaultPort = 8080

  startServer(interface = "0.0.0.0", port = defaultPort) {
    baseRoute ~ authRoute
  }
}

