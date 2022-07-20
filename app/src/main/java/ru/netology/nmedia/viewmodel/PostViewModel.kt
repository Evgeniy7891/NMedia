package ru.netology.nmedia.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.InMemoryPostRepository
import ru.netology.nmedia.repository.PostRepository

val empty = Post( // заглушка для хранилище
    id = 0,
    author = "",
    time = "",
    text = ""
)

class PostViewModel : ViewModel() {
    private val repository: PostRepository = InMemoryPostRepository() // создаем обьект репозитория
    val data = repository.getAll() // используем репозиторий что бы вернуть данные
    val edited = MutableLiveData(empty) // хранилище для поста будет редактирования или создания

    fun likeById(id: Long) {
        repository.likeById(id)
    }

    fun shareById(id: Long) {
        repository.shareById(id)
    }

    fun removeById(id: Long) { // даем подписаться через прослойку репозиторий
        repository.removeById(id)
    }

    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
        Log.d("Tag", "PVModel save")
    }

    fun editContent(content: String) {
        edited.value?.let {
            val trimmed = content.trim()
            if (trimmed == it.text) {
                return
            }
            edited.value = it.copy(text = trimmed)
        }
        Log.d("Tag", "PVM editContent")
    }

    fun edit(post: Post) {
        edited.value = post
        Log.d("Tag", "PVM edit")
    }

    fun clear() {
        edited.value = empty
        Log.d("Tag", "PVM clear")
    }
}