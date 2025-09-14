package com.example.hydra_hymail.data.repository

import com.example.hydra_hymail.model.Post

class PostRepository {
    suspend fun createPost(post: Post) {
        // TODO: Save post to Firebase or REST API
    }

    suspend fun getPosts(): List<Post> {
        // TODO: Retrieve posts from API
        return emptyList()
    }
}
