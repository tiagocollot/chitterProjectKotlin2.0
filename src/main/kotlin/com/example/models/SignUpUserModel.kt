package com.example.models

import org.http4k.template.ViewModel

data class SignUpUserModel (
    val userName: String,
    val email: String,
    val password: String,
    val passwordConfirmation: String,
):ViewModel