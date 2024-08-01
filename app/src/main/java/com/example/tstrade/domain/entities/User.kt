package com.example.tstrade.domain.entities

data class User(
    val id: String,
    val walletId: String
)

data class PartialUser(
    val id: String,
    val name: String,
    val email: String
)