package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class InMemoryPostRepository : PostRepository { // хранить данные в памяти
    var post = Post(
        id = 1,
        author = "Нетология. Университет интернет-профессий будующего!",
        logoAvatar = "",
        time = "21 мая в 18:36",
        text = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетнгу. Затем появились курсы по дизайну, разработке, аналитике и управдению. Мы растем сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остается с нами: мы верим, что в каждом есть сила, которая заставляет хотеть больше, целиться выше, быжать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку переменю - http://netolo.gy/fyb"
    )
    private val data = MutableLiveData(post) // переменная для хранения подписки
    override fun get(): LiveData<Post> = data // возвращает подписку на пост
    override fun like() {
        post.liked = !post.liked
        post.likeCounter = if (post.liked) post.likeCounter + 1 else post.likeCounter - 1
        // post = post.copy(
        //     liked = !post.liked,
        //     likeCounter = if (post.liked) post.likeCounter - 1 else post.likeCounter + 1 )
        data.value = post // передать обновленные данные
    }

    override fun share() {
        post.shareCounter++
        data.value = post
    }
}