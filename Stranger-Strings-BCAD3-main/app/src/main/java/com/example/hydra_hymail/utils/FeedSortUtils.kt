package com.example.hydra_hymail.utils

import com.example.hydra_hymail.model.Post

/**
 * Helper for sorting the feed based on filters:
 * - Most recent
 * - Most popular (likes/comments)
 * - Oldest
 */
object FeedSortUtils {

    fun sortByMostRecent(posts: List<Post>): List<Post> {
        return posts.sortedByDescending { it.createdAt }
    }


    fun sortByPopularity(posts: List<Post>): List<Post> {
        return posts.sortedByDescending { it.reactions.size + it.comments.size }
    }
}
