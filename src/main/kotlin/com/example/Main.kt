import org.http4k.core.*
import org.http4k.lens.FormField
import org.http4k.lens.webForm
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Undertow
import org.http4k.server.asServer
import org.http4k.template.HandlebarsTemplates
import org.http4k.template.ViewModel
import org.http4k.template.viewModel
import java.util.*

val templateRenderer = HandlebarsTemplates().HotReload("src/main/resources")
data class SignUpUserModel(
    val username: String,
    val email: String,
    val password: String,
    val passwordConfirmation: String
) : ViewModel

class SignUpUser {
    private val requiredUsername = FormField.required("username")
    private val requiredEmail = FormField.required("email")
    private val requiredPassword = FormField.required("password")
    private val requiredPasswordConfirmation = FormField.required("password-confirmation")

    val registrationForm = Body.webForm(
        requiredUsername,
        requiredEmail,
        requiredPassword,
        requiredPasswordConfirmation
    ).toLens()
}

val signUp = SignUpUser()

val templates = HandlebarsTemplates().CachingClasspath()

val app: HttpHandler = routes(
    "/" bind Method.GET to { _: Request ->
        Response(Status.OK).body("Hello")
    },
    "/register-user" bind Method.POST to { request: Request ->
        val form = signUp.registrationForm(request)
        val username = signUp.requiredUsername(form)
        val email = signUp.requiredEmail(form)
        val password = signUp.requiredPassword(form)
        val passwordConfirmation = signUp.requiredPasswordConfirmation(form)

        val model = SignUpUserModel(username, email, password, passwordConfirmation)
        val renderedTemplate = HandlebarsTemplates().HotReload.viewModel("SignUpUserModel.hbs", model)

        Response(Status.OK).body(renderedTemplate)
    }
)

fun main() {
    val server = app.asServer(Undertow(9000)).start()

    println("Server started on ${server.port()}")
}