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
    val requiredUser = FormField.required("username")
    val requiredEmail = FormField.required("email")
    val requiredPassword = FormField.required("password")
    val requiredPasswordConfirmation = FormField.required("password-confirmation")

    val registrationForm = Body.webForm(
        Validator.Strict,
        requiredUser,
        requiredEmail,
        requiredPassword,
        requiredPasswordConfirmation
    ).toLens()
}
