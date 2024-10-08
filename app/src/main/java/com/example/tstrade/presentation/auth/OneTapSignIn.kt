package com.example.tstrade.presentation.auth

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.tstrade.domain.model.DataProvider
import com.example.tstrade.domain.model.Response
import com.google.android.gms.auth.api.identity.BeginSignInResult

@Composable
fun OneTapSignIn(
    launch: (result: BeginSignInResult) -> Unit
) {
    when(val oneTapSignInResponse = DataProvider.oneTapSignInResponse) {
        is Response.Loading -> {
            Log.i("Login:OneTap", "Loading")
            AuthLoginProgressIndicator()
        }
        is Response.Success -> oneTapSignInResponse.data?.let { signInResult ->
            LaunchedEffect(signInResult) {
                launch(signInResult)
            }
        }
        is Response.Failure -> LaunchedEffect(Unit) {
            Log.e("Login:OneTap", "${oneTapSignInResponse.e}")
        }
    }
}