package com.example.hydra_hymail.data.repository


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthRepository(private val firebaseAuth: FirebaseAuth) {

    fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser

    suspend fun signInWithGoogle(idToken: String) {
        // TODO: Add Google Sign-In logic
    }

    suspend fun signInWithApple(token: String) {
        // TODO: Add Apple Sign-In logic
    }

    fun signOut() = firebaseAuth.signOut()
}
