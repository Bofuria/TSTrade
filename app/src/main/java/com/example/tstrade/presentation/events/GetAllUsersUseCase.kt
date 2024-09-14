package com.example.tstrade.presentation.events

import com.example.tstrade.di.IoDispatcher
import com.example.tstrade.domain.entities.User
import com.example.tstrade.domain.repository.UserRepositoryImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val userRepositoryImpl: UserRepositoryImpl,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): List<User> = withContext(dispatcher) {
        userRepositoryImpl.getAllUsers()
    }
}