package com.example.hydra_hymail.viewmodel



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hydra_hymail.model.Post

class PostViewModel : ViewModel() {

    private val _currentPost = MutableLiveData<Post?>()
    val currentPost: LiveData<Post?> = _currentPost

    fun createPost(post: Post) {
        _currentPost.value = post
    }

    fun clearPost() {
        _currentPost.value = null
    }
}
