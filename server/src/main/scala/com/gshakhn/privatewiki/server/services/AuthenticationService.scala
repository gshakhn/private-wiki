package com.gshakhn.privatewiki.server.services

import com.gshakhn.privatewiki.server.interactors.AuthenticationInteractorComponent
import com.gshakhn.privatewiki.shared.AuthenticationRequest
import spray.http.HttpResponse
import spray.routing.{HttpService, Route}

trait AuthenticationService extends HttpService {
  this: AuthenticationInteractorComponent =>

  def authRoute: Route = post {
    path("authenticateBinder") {
      extract(_.request.entity.asString) { e =>
        complete {
          val request = upickle.read[AuthenticationRequest](e)
          val response = authenticationInteractor.authenticateBinder(request)
          HttpResponse(entity = upickle.write(response))
        }
      }
    }
  }
}