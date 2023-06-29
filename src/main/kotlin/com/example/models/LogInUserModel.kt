package com.example.models

import org.http4k.template.ViewModel

data class LogInUserModel(
    val username: String,
    val password: String
): ViewModel