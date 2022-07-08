package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        val viewModel by viewModels<PostViewModel>() // получить допуск к вьюМодели
        viewModel.data.observe(this) { post ->
            with(bindingClass) {
                // организуем подписку на изминения типа ВьюМодель
                titleAuthor.text = post.author
                timePosts.text = post.time
                textContent.text = post.text
                imageLikes.setImageResource(if (post.liked) R.drawable.ic_baseline_full_favorite_24 else R.drawable.ic_baseline_favorite_border_24)
                textCountLikes.text =
                    formatCount(post.likeCounter) // конвертирует через метод формат
                textCountShare.text =
                    formatCount(post.shareCounter) // конвертирует черезё метод формат
            }
            bindingClass.imageShare.setOnClickListener() {
                viewModel.share() // реализация счетчика репостов
            }
            bindingClass.imageLikes.setOnClickListener { // реализация лайка
                viewModel.like()
            }
        }
    }
}


