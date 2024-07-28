package com.example.tstrade.domain.entities

data class User(
    val id: String,
    val name: String,
    val email: String,
    val password: String,
    val wallet: Wallet
)