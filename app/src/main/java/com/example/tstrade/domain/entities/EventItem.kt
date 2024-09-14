package com.example.tstrade.domain.entities

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class EventItem(
    val eventId: String = "",
    val author: String = "",
    val name: String = "",
    val description: String = "",
    val attendants: MutableList<String> = mutableListOf(),
    val date: Date? = null,
    val time: String = ""

) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "eventId" to eventId,
            "author" to author,
            "name" to name,
            "description" to description,
            "attendants" to attendants,
            "date" to date,
            "time" to time
        )
    }
}

