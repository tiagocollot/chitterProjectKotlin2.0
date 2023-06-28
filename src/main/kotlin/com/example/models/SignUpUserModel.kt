package com.example.models

import org.http4k.template.ViewModel

data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val password: String
): ViewModel