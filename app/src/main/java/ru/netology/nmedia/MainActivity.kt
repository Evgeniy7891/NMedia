package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

class MainActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        val post = Post(
            author = "Нетология. Университет интернет-профессий будующего!",
            logoAvatar = "",
            time = "21 мая в 18:36",
            text = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетнгу. Затем появились курсы по дизайну, разработке, аналитике и управдению. Мы растем сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остается с нами: мы верим, что в каждом есть сила, которая заставляет хотеть больше, целиться выше, быжать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку переменю - http://netolo.gy/fyb"
        )
        bindingClass.titleAuthor.text = post.author
        bindingClass.timePosts.text = post.time
        bindingClass.textContent.text = post.text
        bindingClass.imageLikes.setOnClickListener { // реализация лайка
            post.liked = !post.liked
            bindingClass.imageLikes.setImageResource(
                if (post.liked == true) {
                    R.drawable.ic_baseline_full_favorite_24
                } else {
                    R.drawable.ic_baseline_favorite_border_24
                }
            )
            if (post.liked == true) { // реализация счетчика лайка
                post.likeCounter++ // инкремент счетчика
                bindingClass.textCountLikes.text =
                    formatCount(post.likeCounter) // конвертирует через метод
            } else {
                post.likeCounter-- // дикрмент счетчика
                bindingClass.textCountLikes.text =
                    formatCount(post.likeCounter) // конвертирует через метод
            }
        }
        bindingClass.imageShare.setOnClickListener() {
            post.shareCounter++ // реализация счетчика репостов
            bindingClass.textCountShare.text =
                formatCount(post.shareCounter) // конвертирует черех метод
        }
    }
}


