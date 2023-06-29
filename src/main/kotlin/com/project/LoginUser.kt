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

class LoginUser {
    val requiredUsername = FormField.required("username")
    val requiredPassword = FormField.required("password")


    val registrationForm = Body.webForm(
        Validator.Strict,
        requiredUsername,
        requiredPassword,
    ).toLens()
}