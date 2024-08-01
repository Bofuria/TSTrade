package com.example.tstrade.presentation.events

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tstrade.SignInObserver
import com.example.tstrade.domain.entities.EventItem
import com.example.tstrade.domain.entities.User
import com.example.tstrade.domain.repository.EventRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val repository: EventRepository
) : ViewModel() {

    private val _state: MutableStateFlow<EventsScreenState> = MutableStateFlow(EventsScreenState.initial())
    val state: StateFlow<EventsScreenState>
        get() = _state

    init {
        viewModelScope.launch(Dispatchers.IO) { // todo: CoroutineDispatcher?
            val data = repository.getAllEvents()
            createNewState(_state.value, EventsScreenUiEvent.ShowData(data))
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

    fun changeAddDialogState(show: Boolean) {
        createNewState(_state.value, EventsScreenUiEvent.OnChangeDialogState(show))
    }

    private fun createNewState(oldState : EventsScreenState, event: EventsScreenUiEvent) {
        when(event) {

            is EventsScreenUiEvent.ShowData -> {
                _state.tryEmit(oldState.copy(isLoading = false, data = event.items))
            }

            is EventsScreenUiEvent.OnChangeDialogState -> _state.tryEmit(oldState.copy(isShowAddDialog = event.show))

            is EventsScreenUiEvent.AddEvent -> {
                val newList = oldState.data.toMutableList()
                newList.add(
                    index = oldState.data.size - 1,
                    element = event.activity
                )
                _state.tryEmit(oldState.copy(
                    data = newList
                ))
            }

            is EventsScreenUiEvent.JoinEvent -> {
                val newList = oldState.data.toMutableList()
                val currentEvent = newList[event.activity.eventId.toInt()]

                // TODO: check if user enrolled or author before adding
                currentEvent.attendants?.add(event.newUser.id)

                _state.tryEmit(oldState.copy(
                    data = newList
                ))
            }

            is EventsScreenUiEvent.LeaveEvent -> {
                val newList = oldState.data.toMutableList()
                val currentEvent = newList[event.activity.eventId.toInt()]
                currentEvent.attendants?.remove(event.leavingUser.id)

                _state.tryEmit(oldState.copy(
                    data = newList
                ))
            }
        }
    }
}