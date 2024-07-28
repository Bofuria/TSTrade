package com.example.tstrade.presentation.auth

import android.util.Log
import androidx.compose.runtime.Composable
import com.example.tstrade.domain.model.DataProvider
import com.example.tstrade.domain.model.Response
import com.google.android.gms.auth.api.signin.GoogleSignIn

@Composable
fun GoogleSignIn(
    launch: () -> Unit
) {
    when(val signInWithGoogleResponse = DataProvider.googleSignInResponse) {
        is Response.Loading -> {
            Log.i("Login:GoogleSignIn", "Loading")
            AuthLoginProgressIndicator()
        }
        is Response.Success -> signInWithGoogleResponse.data?.let { authResult ->
            Log.i("Login:GoogleSignIn", "Success: $authResult")
            launch()
        }
        is Response.Failure -> {
            Log.e("Login:GoogleSignIn", "${signInWithGoogleResponse.e}")
        }
    }
}