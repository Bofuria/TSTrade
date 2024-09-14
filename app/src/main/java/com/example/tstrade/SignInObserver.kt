package com.example.tstrade

import android.util.Log
import com.example.tstrade.domain.model.DataProvider
import com.example.tstrade.domain.model.Response
import com.example.tstrade.domain.repository.UserRepositoryImpl
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

class SignInObserver @Inject constructor(
    private val auth: FirebaseAuth,
    private val userRepositoryImpl: UserRepositoryImpl,

    private var oneTapClient: SignInClient,
    @Named("signInRequest")
    private var signInRequest: BeginSignInRequest,
    @Named("signUpRequest")
    private var signUpRequest: BeginSignInRequest
) {

    fun getAuthState(viewModelScope: CoroutineScope) = callbackFlow {
        val authStateListener = AuthStateListener { auth ->
            trySend(auth.currentUser)
        }

        auth.addAuthStateListener(authStateListener)

        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), auth.currentUser)

    suspend fun onTapSignIn(): Response<BeginSignInResult> {
        return try {
            val signInResult = oneTapClient.beginSignIn(signInRequest).await()
            Response.Success(signInResult)
        } catch (e: Exception) {
            try {
                val signUpResult = oneTapClient.beginSignIn(signUpRequest).await()
                Response.Success(signUpResult)
            } catch (e: Exception) {
                Response.Failure(e)
            }
        }
    }

    suspend fun signInWithGoogle(credential: SignInCredential): Response<AuthResult> {
        val googleCredential = GoogleAuthProvider
            .getCredential(credential.googleIdToken, null)
        return authenticateUser(googleCredential)
    }

    private suspend fun authenticateUser(credential: AuthCredential): Response<AuthResult> {
        return if (auth.currentUser != null) {
            authLink(credential)
        } else {
            authSignIn(credential)
        }
    }

    private suspend fun authSignIn(credential: AuthCredential): Response<AuthResult> {
        return try {
            val authResult = auth.signInWithCredential(credential).await()
            DataProvider.updateAuthState(authResult?.user)
            Response.Success(authResult)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    private suspend fun authLink(credential: AuthCredential): Response<AuthResult> {
        return try {
            val authResult = auth.currentUser?.linkWithCredential(credential)?.await()
            DataProvider.updateAuthState(authResult?.user)
            Response.Success(authResult)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    suspend fun signInAnonymously(): Response<AuthResult> {
        return try {
            val authResult = auth.signInAnonymously().await()
            authResult?.user?.let { user ->
                Log.i("INFO", "FirebaseAuthSuccess: Anonymous UID: ${user.uid}")
            }
            Response.Success(authResult)
        } catch (e: Exception) {
            Log.e("Error", "Error")
            Response.Failure(e)
        }
    }

    suspend fun signOut(): Response<Boolean> {
        return try {
            oneTapClient.signOut().await()
            auth.signOut()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

}