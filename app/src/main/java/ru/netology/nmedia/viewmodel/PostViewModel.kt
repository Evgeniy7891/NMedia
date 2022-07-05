package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.InMemoryPostRepository
import ru.netology.nmedia.repository.PostRepository

class PostViewModel : ViewModel() {
    private val repository: PostRepository = InMemoryPostRepository() // создаем обьект репозитория
    val data = repository.get() // используем репозиторий что бы вернуть данные
    fun like() {
        repository.like() // пробросить события в UI в следующий слой (просходит взаимодействия ВьюМодели с репозиторием)
    }
    fun share() {
        repository.share()
    }
}