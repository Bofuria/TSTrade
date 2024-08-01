package com.example.tstrade.domain.entities

data class Wallet (
    val id: String = "",
    val reputation: Map<MoneyType, Int> = emptyMap()
)