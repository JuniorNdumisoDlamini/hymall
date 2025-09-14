package com.example.hydra_hymail.data.repository



import com.example.hydra_hymail.model.User

class ProfileRepository {
    suspend fun updateProfile(user: User) {
        // TODO: Update user profile in DB
    }

    suspend fun getProfile(userId: String): User? {
        // TODO: Get user profile from DB
        return null
    }
}
