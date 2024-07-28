package com.example.tstrade.presentation.events

import com.example.tstrade.domain.entities.User
import com.example.tstrade.domain.entities.Wallet
import java.util.Date

sealed class EventsScreenEntity {
    object AddEventButtonItem : EventsScreenEntity()
    data class EventItem(
        val name: String,
        val description: String,
        val attendants: List<User>,
        val bounty: Wallet,
        val date: Date,
        val author: User
    )
}