package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>> // получение подписки типа пост
    fun like() // функция по постоновке лайка
    fun share() // функция по постоновке репоста
    fun likeById(id: Long) // лайкаем по Айди
    fun shareById(id: Long) // репост по айди
    fun removeById(id: Long) // функция удаления поста
    fun save(post: Post) // функция сохронения поста
}