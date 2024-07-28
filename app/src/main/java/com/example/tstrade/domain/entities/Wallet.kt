package com.example.tstrade.domain.entities

data class Wallet (
    val id: Int,
    val amount: Map<MoneyType, Int>
)