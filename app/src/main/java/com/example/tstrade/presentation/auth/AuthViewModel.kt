package com.example.tstrade.presentation.auth

import androidx.lifecycle.ViewModel
import com.example.tstrade.SignInObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import com.example.tstrade.domain.model.DataProvider
import com.example.tstrade.domain.model.Response
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val observer: SignInObserver,
    val oneTapClient: SignInClient
) :ViewModel() {

    val currentUser = fetchCurrentUser()

    private fun fetchCurrentUser() = observer.getAuthState(viewModelScope)

    fun oneTapSignIn() = CoroutineScope(Dispatchers.IO).launch {
        DataProvider.oneTapSignInResponse = Response.Loading
        DataProvider.oneTapSignInResponse = observer.onTapSignIn()
    }

    fun signInWithGoogle(credential: SignInCredential) = CoroutineScope(Dispatchers.IO).launch {
        DataProvider.googleSignInResponse = Response.Loading
        DataProvider.googleSignInResponse = observer.signInWithGoogle(credential)
    }
}