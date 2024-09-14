package com.example.tstrade.presentation.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tstrade.domain.entities.EventItem
import com.example.tstrade.domain.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val getAllEventsUseCase: GetAllEventsUseCase,
    private val saveEventUseCase: SaveEventUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<EventsScreenState> = MutableStateFlow(EventsScreenState.initial())
    val state: StateFlow<EventsScreenState>
        get() = _state

    init {
        viewModelScope.launch {
            val data = getAllEventsUseCase()
            val users = getAllUsersUseCase()
            createNewState(_state.value, EventsScreenUiEvent.ShowData(data, users))
        }
    }

//    fun showData(items: List<EventItem>) {
//        createNewState(_state.value, EventsScreenUiEvent.ShowData(items))
//    }

    fun joinEvent(activity: EventItem, newUser : User) {
        createNewState(_state.value, EventsScreenUiEvent.JoinEvent(activity, newUser))
   }

    fun leaveEvent(activity: EventItem, leavingUser : User) {
        createNewState(_state.value, EventsScreenUiEvent.LeaveEvent(activity, leavingUser))
    }

    fun createEvent(activity: EventItem) {
        createNewState(_state.value, EventsScreenUiEvent.AddEvent(activity))
    }

    fun changeAddDialogState(show: Boolean) {
        createNewState(_state.value, EventsScreenUiEvent.OnChangeDialogState(show))
    }

    private fun createNewState(oldState : EventsScreenState, event: EventsScreenUiEvent) {
        when(event) {

            is EventsScreenUiEvent.ShowData -> {
                _state.tryEmit(oldState.copy(isLoading = false, events = event.items, users = event.users))
            }

            is EventsScreenUiEvent.OnChangeDialogState -> _state.tryEmit(oldState.copy(isShowAddDialog = event.show))

            is EventsScreenUiEvent.AddEvent -> {
                val newItem = event.activity

                viewModelScope.launch {
                    saveEventUseCase(newItem)
                }

                val newList = oldState.events.toMutableList()
                newList.add(
                    index = oldState.events.size - 1,
                    element = newItem
                )
                _state.tryEmit(oldState.copy(
                    events = newList
                ))
            }

            is EventsScreenUiEvent.JoinEvent -> {
                val newList = oldState.events.toMutableList()
                val currentEvent = newList[event.activity.eventId.toInt()]

                // TODO: check if user enrolled or author before adding
                currentEvent.attendants?.add(event.newUser.id)

                _state.tryEmit(oldState.copy(
                    events = newList
                ))
            }

            is EventsScreenUiEvent.LeaveEvent -> {
                val newList = oldState.events.toMutableList()
                val currentEvent = newList[event.activity.eventId.toInt()]
                currentEvent.attendants?.remove(event.leavingUser.id)

                _state.tryEmit(oldState.copy(
                    events = newList
                ))
            }
        }
    }
}