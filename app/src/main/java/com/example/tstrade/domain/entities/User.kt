package com.example.tstrade.domain.entities

data class User(
    val id: String = "",
    val name: String = "",
    val reputation: Int = 0,
    val cookies: Int = 0
)

data class PartialUser(
    val id: String,
    val name: String,
    val email: String
)