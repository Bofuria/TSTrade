package com.example.tstrade.domain.repository

import android.util.Log
import com.example.tstrade.domain.entities.EventItem
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor (private val firestore: FirebaseFirestore) {

    companion object { // TODO: must be moved outside for every class that logs to use it
        private const val ERROR_TAG = "ERROR"
        private const val SUCCESS_TAG = "SUCCESS"
    }


    // events
    suspend fun getAllEvents(): List<EventItem> {
        val eventsList = mutableListOf<EventItem>()

        firestore.collection("events")
            .get()
            .addOnSuccessListener { result ->
                if(!result.isEmpty) {
                    val documents = result.documents
                    for(item in documents) {
                        val eventItem = item.toObject(EventItem::class.java)
                        eventItem?.let { eventsList.add(it) }
                    }
                }
            }
            .addOnFailureListener { error ->
                Log.e(ERROR_TAG, "Error occurred during fetching all events: $error")
            }

        return eventsList
    }

    // events/id
    suspend fun getEvent(id: String) : EventItem? {
        var event: EventItem? = null

        firestore.collection("events")
            .document(id)
            .get()
            .addOnSuccessListener { result ->
                    event = result.toObject(EventItem::class.java)
            }
            .addOnFailureListener { error ->
                Log.e(ERROR_TAG, "Error occurred during fetching event $id: $error")
            }

        return event
    }


    suspend fun saveEvent(eventItem: EventItem) {
        firestore.collection("events")
            .document(eventItem.eventId)
            .set(eventItem.toMap())
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    Log.i(SUCCESS_TAG, "Saved event successfully")
                } else {
                    Log.e(ERROR_TAG, "Error saving event: $task")
                }
            }
    }

    suspend fun deleteEvent(id: String) {
        firestore.collection("events")
            .document(id)
            .delete()
            .addOnSuccessListener {
                Log.i(SUCCESS_TAG, "Event deleted successfully")
            }
            .addOnFailureListener{ error ->
                Log.e(ERROR_TAG, "Error deleting event #$id: $error")
            }
    }

    suspend fun updateEvent(id: String, newEvent: EventItem) {
        val updates = newEvent.toMap().filterValues { it != null }

        firestore.collection("events")
            .document(id)
            .update(updates)
            .addOnSuccessListener {
                Log.i(SUCCESS_TAG, "Updated event $id successfully")
            }
            .addOnFailureListener { error ->
                Log.e(ERROR_TAG, "Failed to update $id event: $error")
            }
    }
}