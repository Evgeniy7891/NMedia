package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val logoAvatar: String = "",
    val time: String,
    val text: String,
    val likeCounter: Long = 999, // счетчик лайков
    val shareCounter: Long = 999_998, // счетчик репостов
    val liked: Boolean = false
)