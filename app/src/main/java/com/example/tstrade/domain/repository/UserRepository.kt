package com.example.tstrade.domain.repository

import com.example.tstrade.domain.entities.User
import com.example.tstrade.domain.entities.Wallet
import com.example.tstrade.presentation.auth.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val db: FirebaseFirestore,
) {

    fun fetchUser(id: String): User? {
        var user: User? = null

         db.collection("users")
            .document(id)
            .get()
            .addOnSuccessListener { result ->
                user = result.toObject(User::class.java)!!
            }

        return user
    }

    fun createUser(firebaseUser: FirebaseUser) {
        val Wallet = Wallet( id = firebaseUser.uid )
    }


}