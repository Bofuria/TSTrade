package com.example.tstrade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.example.tstrade.domain.model.AuthState
import com.example.tstrade.domain.model.DataProvider
import com.example.tstrade.presentation.auth.AuthViewModel
import com.example.tstrade.presentation.auth.LoginScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.firestoreSettings
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val currentUser = authViewModel.currentUser.collectAsState().value
            DataProvider.updateAuthState(currentUser)

            if(DataProvider.authState != AuthState.SignedOut) MyApp(firebaseAuth.currentUser!!)
            else LoginScreen(authViewModel)
        }
    }
}