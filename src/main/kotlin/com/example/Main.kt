package com.example
import com.example.models.LogInUserModel
import com.example.models.SignUpUserModel
import com.project.LoginUser
import com.project.SignUpUser
import org.http4k.core.*
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Undertow
import org.http4k.server.asServer
import org.http4k.template.HandlebarsTemplates

val templateRenderer = HandlebarsTemplates().HotReload("src/main/resources")

val signUpUser = SignUpUser()
val loginUser = LoginUser()

val app: HttpHandler = routes(
    "/" bind Method.GET to { _: Request ->
        Response(Status.OK).body("Hello")
    },
    "/register-user" bind Method.GET to { _: Request ->
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
    },
    "/login" bind Method.GET to { _: Request ->
        val viewModel = LogInUserModel("", "")
        val renderTemplate = templateRenderer(viewModel)
        Response(Status.OK).body(renderTemplate)
    },
    "/login" bind Method.POST to { request: Request ->
        val form = loginUser.registrationForm(request)
        val username = loginUser.requiredUsername(form)
        val password = loginUser.requiredPassword(form)

        // Perform login validation and authentication logic here

        if (isValidLogin(username, password)) {
            Response(Status.OK).body("Login successful!\nUsername: $username")
        } else {
            Response(Status.UNAUTHORIZED).body("Invalid credentials")
        }
    }
)
fun isValidLogin(username: String, password: String): Boolean {
    // Implement your login validation and authentication logic here
    // This is a basic example, you should replace it with your actual implementation

    // Check if the username and password match a valid user in your system
    val validUsername = "admin"
    val validPassword = "password"

    return username == validUsername && password == validPassword
}


fun main() {
    val server = app.asServer(Undertow(9000)).start()

    println("Server started on ${server.port()}")
}
