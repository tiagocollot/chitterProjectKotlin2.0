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

val app: HttpHandler = routes(
    "/" bind GET to { request: Request ->
        Response(OK).body("Hello")
    }
)

fun main() {
    val server = app.asServer(Undertow(9000)).start()

    println("Server started on " + server.port())
}
