package com.example.hydra_hymail.data.local



import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.hydra_hymail.model.User

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query(value = "SELECT * FROM users WHERE id = :userId")
    suspend fun getUser(userId: String): User?
}
