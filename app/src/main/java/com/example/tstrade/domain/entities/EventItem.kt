package com.example.tstrade.domain.entities

import java.util.Date

data class EventItem(
    val eventId: Int,
    val author: User,
    val name: String,
    val description: String,
    val attendants: MutableList<User>, // uids
//    val bounty: Wallet,
    val date: Date
)