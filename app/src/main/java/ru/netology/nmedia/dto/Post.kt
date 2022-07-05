package ru.netology.nmedia.dto

data class Post(
    val id: Long = 0,
    val author: String,
    val logoAvatar: String,
    val time: String,
    val text: String,
    var likeCounter: Long = 999, // счетчик лайков
    var shareCounter: Long = 999_998, // счетчик репостов
    var liked: Boolean = false
)