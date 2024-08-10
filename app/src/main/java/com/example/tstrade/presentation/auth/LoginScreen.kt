package com.example.tstrade.presentation.auth

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tstrade.domain.model.AuthState
import com.example.tstrade.domain.model.DataProvider
import com.example.tstrade.domain.model.Response
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.common.api.ApiException

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    loginState: MutableState<Boolean>? = null
) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK) {
            try {
                val credentials = authViewModel.oneTapClient.getSignInCredentialFromIntent(result.data)
                authViewModel.signInWithGoogle(credentials)
            } catch (e: ApiException) {
                Log.e("LoginScreen:Launcher","Login One-tap $e")
            }
        } else if (result.resultCode == Activity.RESULT_CANCELED) {
            Log.e("LoginScreen:Launcher","OneTapClient Canceled")
        }
    }

    fun launch(signInResult: BeginSignInResult) {
        val intent = IntentSenderRequest.Builder(signInResult.pendingIntent.intentSender).build()
        launcher.launch(intent)
    }

    Scaffold (
        containerColor = MaterialTheme.colorScheme.primary
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .wrapContentSize(Alignment.TopCenter),
            Arrangement.spacedBy(8.dp),
            Alignment.CenterHorizontally
        ) {

            Button(
                onClick = { authViewModel.oneTapSignIn() },
                modifier = Modifier
                    .size(width = 300.dp, height = 50.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                )
            ) {
                Text(
                    text = "Sign in with Google",
                    modifier = Modifier.padding(6.dp),
                    color = Color.White.copy(alpha = 0.5f)
                )
            }
        }
    }

    OneTapSignIn(
        launch = {
            launch(it)
        }
    )
    GoogleSignIn {
        loginState?.let {
            it.value = false
        }
    }

}

@Composable
fun AuthLoginProgressIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.tertiary,
            strokeWidth = 5.dp
        )
    }
}

@Composable
fun loginPasswordForm() { // TODO: Implement

}