package com.example
import com.project.SignUpUser
import com.example.models.SignUpUserModel
import org.http4k.core.*
import org.http4k.lens.FormField
import org.http4k.lens.Validator
import org.http4k.lens.webForm
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Undertow
import org.http4k.server.asServer
import org.http4k.template.HandlebarsTemplates
import org.http4k.template.ViewModel
import java.util.*

val templateRenderer = HandlebarsTemplates().HotReload("src/main/resources")

val signUpUser = SignUpUser()

val app: HttpHandler = routes(
    "/" bind Method.GET to { _: Request ->
        Response(Status.OK).body("Hello")
    },
    "/register-user" bind Method.GET to { request: Request ->
       /* val form = signUpUser.registrationForm(request)
        val username = signUpUser.requiredUser(form)
        val email = signUpUser.requiredEmail(form)
        val password = signUpUser.requiredPassword(form)
        val passwordConfirmation = signUpUser.requiredPasswordConfirmation(form)
*/
        val viewModel = SignUpUserModel("", "", "", "")
        val renderTemplate = templateRenderer(viewModel)
        Response(Status.OK).body(renderTemplate)
    },
    "/register-user" bind Method.POST to { request: Request ->
        val form = signUpUser.registrationForm(request)
        val username = signUpUser.requiredUser(form)
        val email = signUpUser.requiredEmail(form)
        //val password = signUpUser.requiredPassword(form)
        //val passwordConfirmation = signUpUser.requiredPasswordConfirmation(form)
/*
        val viewModel = SignUpUserModel("", "", "", "")
        val renderTemplate = templateRenderer(viewModel)*/
        Response(Status.OK).body("The form was successfully submitted!\nusername:${username} email:${email} ")
    }
)

fun main() {
    val server = app.asServer(Undertow(9000)).start()

    println("Server started on ${server.port()}")
}