package com.example.tstrade.domain.repository

import com.example.tstrade.domain.entities.EventItem
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class EventRepository @Inject constructor (firestore: FirebaseFirestore) {

    suspend fun getAllEvents(): List<EventItem> {

        return emptyList()
    }

//    suspend fun getEvent(id: Int) : EventItem {
//        return
//    }

    suspend fun saveEvent(eventItem: EventItem) {

    }

    suspend fun deleteEvent(eventItem: EventItem) {

    }

    suspend fun editEvent(id: Int, eventItem: EventItem) {

    }
}