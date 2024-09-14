package com.example.tstrade.presentation.events

import com.example.tstrade.di.IoDispatcher
import com.example.tstrade.domain.entities.EventItem
import com.example.tstrade.domain.repository.EventRepositoryImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveEventUseCase @Inject constructor(
    private val eventRepositoryImpl: EventRepositoryImpl,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(eventItem: EventItem) = withContext(dispatcher) {
        eventRepositoryImpl.saveEvent(eventItem)
    }
}