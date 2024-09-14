package com.example.tstrade.domain.repository

import android.util.Log
import com.example.tstrade.domain.entities.User
import com.example.tstrade.domain.entities.Wallet
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface IUserRepository {
    suspend fun getAllUsers(): List<User>
    suspend fun fetchUser(id: String) : User?
    suspend fun createUser(firebaseUser: FirebaseUser)
}

class UserRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
): IUserRepository {

    override suspend fun getAllUsers(): List<User> {
        val users = mutableListOf<User>()

        val result = db.collection("users")
            .get()
            .await()

        try {
            if(!result.isEmpty) {
                val documents = result.documents
                for(user in documents) {
                    val fetchedUser = user.toObject(User::class.java)
                    fetchedUser?.let {
                        users.add(fetchedUser)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("Error: ", e.toString())
        }

        return users
    }

    override suspend fun fetchUser(id: String): User? {
        var user: User? = null

         val result = db.collection("users")
            .document(id)
            .get()
            .await()

         try {
             if(!result.exists()) {
                 user = result.toObject(User::class.java)!!
             }
         } catch (e: Exception) {
             Log.e("Error: ", e.toString())
         }

        return user
    }

    override suspend fun createUser(firebaseUser: FirebaseUser) {
        val wallet = Wallet( id = firebaseUser.uid )
    }


}