package com.example.tstrade.domain.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.StateFlow

enum class AuthState {
    Authenticated,
    SignedIn,
    SignedOut
}


object DataProvider {

    var anonymousSignInResponse by mutableStateOf<Response<AuthResult>>(Response.Success(null))
    var oneTapSignInResponse by mutableStateOf<Response<BeginSignInResult>>(Response.Success(null))
    var googleSignInResponse by mutableStateOf<Response<AuthResult>>(Response.Success(null))
    var signOutResponse by mutableStateOf<Response<Boolean>>(Response.Success(false))

    var user by mutableStateOf<FirebaseUser?>(null)
    var isAuthenticated by mutableStateOf(false)

    var isAnonymous by mutableStateOf(false)

    var authState by mutableStateOf(AuthState.SignedOut)
        private set

    fun updateAuthState(user: FirebaseUser?) {
        this.user = user
        isAuthenticated = user != null

        authState = if(isAuthenticated) {
            if(isAnonymous) AuthState.Authenticated else AuthState.SignedIn
        } else {
            AuthState.SignedOut
        }

         if(isAuthenticated) {
             authState = AuthState.SignedIn
        }
    }

}