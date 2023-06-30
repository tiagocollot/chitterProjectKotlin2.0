package com.example
// Import required classes and models
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

// Create an instance of HandlebarsTemplates for template rendering
val templateRenderer = HandlebarsTemplates().HotReload("src/main/resources")

// Create instances of SignUpUser and LoginUser
val signUpUser = SignUpUser()
val loginUser = LoginUser()

// Define the main HTTP handler for the application
val app: HttpHandler = routes(
    // Define the root endpoint
    "/" bind Method.GET to { _: Request ->
        Response(Status.OK).body("Hello")
    },
    // Define the register-user endpoint for GET requests
    "/register-user" bind Method.GET to { _: Request ->
        // Create a ViewModel for SignUpUserModel with empty values
        val viewModel = SignUpUserModel("", "", "", "")

        // Render the template using the ViewModel and return the response
        val renderTemplate = templateRenderer(viewModel)
        Response(Status.OK).body(renderTemplate)
    },
    // Define the register-user endpoint for POST requests
    "/register-user" bind Method.POST to { request: Request ->
        // Extract form data and required fields from the request
        val form = signUpUser.registrationForm(request)
        val username = signUpUser.requiredUser(form)
        //val email = signUpUser.requiredEmail(form)

        // Create a redirect response to "/login"
        val redirectUrl = "/login"
        val redirectResponse = Response(Status.SEE_OTHER).header("Location", redirectUrl)
        // Return the redirect response
        redirectResponse
        // Return a response indicating successful form submission
       // Response(Status.OK).body("The form was successfully submitted!\nusername:${username} email:${email} ")
    },
    // Define the login endpoint for GET requests
    "/login" bind Method.GET to { _: Request ->
        // Create a ViewModel for LogInUserModel with empty values
        val viewModel = LogInUserModel("", "")
        val renderTemplate = templateRenderer(viewModel)

        // Render the template using the ViewModel and return the response
        Response(Status.OK).body(renderTemplate)
    },
    // Define the login endpoint for POST requests
    "/login" bind Method.POST to { request: Request ->
        // Extract form data and required fields from the request
        val form = loginUser.registrationForm(request)
        val username = loginUser.requiredUsername(form)
        val password = loginUser.requiredPassword(form)

        // Perform login validation and authentication logic
        // Check if the login is valid using the isValidLogin function
        if (isValidLogin(username, password)) {
            // Return a response indicating successful login
            Response(Status.OK).body("Login successful!\nUsername: $username")
        } else {
            // Return a response indicating invalid credentials
            Response(Status.UNAUTHORIZED).body("Invalid credentials")
        }
    }
)


// Function to validate login credentials
fun isValidLogin(username: String, password: String): Boolean {
    // Check if the username and password match a valid user in your system
    val validUsername = "admin"
    val validPassword = "password"

    return username == validUsername && password == validPassword
}

// Main function to start the server
fun main() {
    // Start the server on port 9000
    val server = app.asServer(Undertow(9000)).start()

    // Print server start message
    println("Server started on ${server.port()}")
}