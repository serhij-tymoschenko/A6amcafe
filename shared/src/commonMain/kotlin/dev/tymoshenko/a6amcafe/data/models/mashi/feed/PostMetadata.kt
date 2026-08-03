package dev.tymoshenko.a6amcafe.data.models.mashi.feed

import dev.tymoshenko.a6amcafe.data.models.users.UserDetails

data class PostMetadata(
    val userDetails: UserDetails,
    val timestamp: Long,
    val rateCount: Int
)