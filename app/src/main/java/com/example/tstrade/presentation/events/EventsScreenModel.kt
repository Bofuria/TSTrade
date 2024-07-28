package com.example.tstrade.presentation.events

import com.example.tstrade.domain.entities.EventItem
import com.example.tstrade.domain.entities.User
import javax.annotation.concurrent.Immutable

@Immutable
sealed class EventsScreenUiEvent {
    data class JoinEvent(val activity: EventItem, val newUser: User) : EventsScreenUiEvent()
    data class LeaveEvent(val activity: EventItem, val leavingUser: User) : EventsScreenUiEvent()
    data class OnChangeDialogState(val show: Boolean) : EventsScreenUiEvent()
    data class AddEvent(val activity: EventItem) : EventsScreenUiEvent()
}

data class EventsScreenState(
    val isLoading: Boolean,
    val data: List<EventItem>,
    val isShowAddDialog : Boolean
) {
    companion object {
        fun initial() = EventsScreenState(
            isLoading = true,
            data = emptyList(),
            isShowAddDialog = false
        )
    }
}