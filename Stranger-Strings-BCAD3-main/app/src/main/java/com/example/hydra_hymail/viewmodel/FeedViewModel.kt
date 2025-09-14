package com.example.hydra_hymail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hydra_hymail.model.Post

class FeedViewModel : ViewModel() {

    private val _posts = MutableLiveData<List<Post>>(emptyList())
    val posts: LiveData<List<Post>> = _posts

    fun setFeed(newPosts: List<Post>) {
        _posts.value = newPosts
    }

    fun addPost(post: Post) {
        _posts.value = _posts.value?.plus(post)
    }
}
