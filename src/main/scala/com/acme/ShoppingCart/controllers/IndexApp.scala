package com.acme.ShoppingCart.controllers

import com.twitter.finatra.Controller
import com.acme.ShoppingCart.exception.Unauthorized

class IndexApp extends Controller {

  /**
   * Single page application on AngularJS
   *
   * curl http://localhost:7070/
   */
  get("/") { request =>
    render.static("index.html").toFuture
  }

  /**
   * Custom 404s
   *
   * curl http://localhost:7070/notfound
   */
  notFound { request =>
    render.status(404).plain("not found").toFuture
  }

  error { request =>
    request.error match {
      case Some(e:Unauthorized) =>
        log.error(request.error.toString, "Not Authorized!")
        render.status(401).plain("Not Authorized!").toFuture
      case _ =>
        log.error(request.error.toString, "Something went wrong!")
        render.status(500).plain("Something went wrong!").toFuture
    }
  }
}