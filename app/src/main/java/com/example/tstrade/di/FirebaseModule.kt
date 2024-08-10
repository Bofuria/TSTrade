package com.example.tstrade.di

import com.example.tstrade.SignInObserver
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.persistentCacheSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth  {
        val auth = FirebaseAuth.getInstance()
        auth.useEmulator("127.0.0.1", 9099)
        return auth
    }

    @Provides
    fun provideFirestore(): FirebaseFirestore {
        val firestore = Firebase.firestore
        firestore.useEmulator("127.0.0.1", 8080)
        return firestore
    }

}