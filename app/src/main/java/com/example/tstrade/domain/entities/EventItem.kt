package com.example.tstrade.domain.entities

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class EventItem(
    val eventId: String = "",
    val author: String = "",
    val name: String = "",
    val description: String = "",
    var attendants: MutableList<String>? = null,
    @ServerTimestamp val date: Date? = null
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "eventId" to eventId,
            "author" to author,
            "name" to name,
            "description" to description,
            "attendants" to attendants,
            "date" to date
        )
    }
}

