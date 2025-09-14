package com.example.hydra_hymail.data.local



import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.hydra_hymail.model.Post

@Dao
interface PostDao {
    @Insert
    suspend fun insert(post: Post)

    @Query("SELECT * FROM post")
    suspend fun getAllPosts(): List<Post>
}
