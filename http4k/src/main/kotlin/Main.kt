package com.example

import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.lens.Query
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Undertow
import org.http4k.server.asServer

// A GET request is being made
//

fun handleRequestAndReturnResponse(request: Request): Response {
    return Response(OK).body("Hello Kotlin")
}

// HttpHandler is a special type from the http4k library
// and an HttpHandler transforms a Request into a Response
val app: HttpHandler = routes(

    // When a GET request is being made, execute this code
    "/" bind GET to ::handleRequestAndReturnResponse
)

fun main() {
    // Run the 'app' as a HTTP server on port 9000
    val server = app.asServer(Undertow(9000)).start()

    // Logging the port on which the server is running
    println("Server started on " + server.port())
}