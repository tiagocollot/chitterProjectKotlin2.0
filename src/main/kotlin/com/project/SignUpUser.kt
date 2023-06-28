package com.project

import org.http4k.core.Body
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.cookie.Cookie
import org.http4k.core.cookie.cookie
import org.http4k.lens.FormField
import org.http4k.lens.Validator
import org.http4k.lens.webForm
import java.util.*

class SignUpUser {
    private val requiredNewUser = FormField.required("username")
    private val requiredPassword = FormField.required("password")
    private val requiredPasswordConfirmation = FormField.required("password-confirmation")

    val registrationForm = Body.webForm(
        Validator.Strict,
        requiredNewUser,
        requiredPassword,
        requiredPasswordConfirmation
    ).toLens()
}

fun createUser(request: Request): Response {
    val form = registrationForm(request)
    val userName = requiredNewsUser(form)
    val password = requiredNewPassword(form)
    database_connection().insert(Users){
        set(it.name, userName)
        set(it.password, password)
    }
    val insertedUserId = database_connection().from(Users).select(Users.id)
        .where { Users.name eq userName }
        .map { it[Users.id] }
        .firstOrNull()
    val sessionId = UUID.randomUUID().toString()

    if (insertedUserId != null) {
        sessionRegistry.put(sessionId, insertedUserId)
    }
    val response = Response(Status.SEE_OTHER)
        .header("Location", "/chitter")
        .cookie(Cookie("session_id", sessionId))
        .body("")
    return response

}

fun registerPage(request: Request): Response {
    val cookie = request.cookie("session_id")
    val sessionId =cookie?.value
    return if (sessionId != null){
        Response(Status.SEE_OTHER)
            .header("Location", "/chitter")
            .body("")
    }else{
        val credentials = RegisterUserModel("", "", "", "")
        val theContent = templateRenderer(credentials)
        return Response(Status.OK).body(theContent)
    }
}

}