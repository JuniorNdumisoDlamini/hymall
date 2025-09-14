package com.example.hydra_hymail.data.local



import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hydra_hymail.model.Post
import com.example.hydra_hymail.model.User

@Database(entities = [Post::class, User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun userDao(): UserDao
}
