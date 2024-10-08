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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth  {
        val auth = FirebaseAuth.getInstance()
//        auth.useEmulator("10.0.2.2", 9099)
        return auth
    }

    @Provides
    fun provideFirestore(): FirebaseFirestore {
        val firestore = Firebase.firestore
        firestore.useEmulator("10.0.2.2", 8080)
        return firestore
    }

}