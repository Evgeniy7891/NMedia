package ru.netology.nmedia.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class InMemoryPostRepository : PostRepository { // реализует интерфейс и хранить данные в памяти
    private var nextId = 1L
    private var posts = listOf(
        Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будующего!",
            time = "21 мая в 18:36",
            text = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетнгу. Затем появились курсы по дизайну, разработке, аналитике и управдению. Мы растем сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остается с нами: мы верим, что в каждом есть сила, которая заставляет хотеть больше, целиться выше, быжать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку переменю - http://netolo.gy/fyb"
        ),
        Post(
            id = 2,
            author = "Нетология. Университет интернет-профессий будующего!",
            time = "18 сентября в 10:12",
            text = "Знаний хватит на все: на следующей неделе разбираемся с разработкой мобильных приложений, учимся рассказывать истории и составлять PR-стратегию прямо на бесплатных занятиях \\uD83D\\uDC47\""
        ),
        Post(
            id = 3,
            author = "Нетология. Университет интернет-профессий будующего!",
            time = "29 сентября в 10:29",
            text = "Языков программирования много, и выбрать какой-то один бывает нелегко. Собрали подборку статей, которая поможет вам начать, если вы остановили свой выбор на JavaScript.",
            likeCounter = 123,
            shareCounter = 55
        ),
        Post(
            id = 4,
            author = "Нетология. Университет интернет-профессий будующего!",
            time = "30 сентября 12:05",
            text = "Большая афиша мероприятий осени: конференции, выставки и хакатоны для жителей Москвы, Ульяновска и Новосибирска \\uD83D\\uDE09",
            likeCounter = 123094,
            shareCounter = 123123123,
            liked = true
        ),
        Post(
            id = 5,
            author = "Нетология. Университет интернет-профессий будующего!",
            time = "1 октября 22:22",
            text = "Диджитал давно стал частью нашей жизни: мы общаемся в социальных сетях и мессенджерах, заказываем еду, такси и оплачиваем счета через приложения.",
            likeCounter = 14,
            shareCounter = 145,
            liked = true
        ),
    )
    private val data = MutableLiveData(posts) // переменная для хранения подписки
    override fun getAll(): LiveData<List<Post>> = data // возвращает подписку на пост
    override fun like() {
        posts = posts.map {
            it.copy(
                liked = !it.liked,
                likeCounter = if (it.liked) it.likeCounter - 1 else it.likeCounter + 1
            )
        }
        data.value = posts // передать обновленные данные
    }

    override fun share() {
        posts = posts.map {
            it.copy(shareCounter = it.shareCounter + 1)
        }
        data.value = posts
    }

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                liked = !it.liked,
                likeCounter = if (!it.liked) it.likeCounter + 1 else it.likeCounter - 1
            )
        }
        data.value = posts
    }

    override fun shareById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                shareCounter = it.shareCounter + 1
            )
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filterNot { it.id == id }// фильтруем посты
        data.value = posts

    }

    override fun save(post: Post) {
        data.value = if (post.id == 0L)  {
            Log.d("TAG", "IMPR save newPostsave")
            listOf(
                post.copy( // создаем список на основе который уже есть
                    id = posts.firstOrNull()?.id ?: 1L, // добираемся до первого элемента
                    author = "Нетология",
                    time = "2022"
                )
            ) + posts
        } else {
            Log.d("TAG", "IMPR editSave")
            posts.map {
                if (it.id == post.id) it.copy(text = post.text) else it
            }
        }

    }
}

