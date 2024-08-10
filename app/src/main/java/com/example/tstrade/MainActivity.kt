package com.example.tstrade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.example.tstrade.domain.model.DataProvider
import com.example.tstrade.presentation.auth.AuthViewModel
import com.example.tstrade.presentation.auth.LoginScreen
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.firestoreSettings
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val currentUser = authViewModel.currentUser.collectAsState().value
            DataProvider.updateAuthState(currentUser)

            if(DataProvider.isAuthenticated) MyApp()
            else LoginScreen(authViewModel)
        }
    }
}