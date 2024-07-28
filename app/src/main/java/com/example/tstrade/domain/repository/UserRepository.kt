package com.example.tstrade.domain.repository

import com.example.tstrade.domain.entities.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {

//    val currentUser: User? =
//        firebaseAuth.currentUser?.let {
//            User(
//                it.uid,
//                it.displayName,
//                it.email,
//                )
//        }


}