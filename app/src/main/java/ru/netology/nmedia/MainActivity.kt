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
        viewModel.data.observe(this) { post -> // организуем подписку на изминения типа ВьюМодель
            bindingClass.titleAuthor.text = post.author
            bindingClass.timePosts.text = post.time
            bindingClass.textContent.text = post.text
            val likeImage = if (post.liked == true) {
                R.drawable.ic_baseline_full_favorite_24
            } else {
                R.drawable.ic_baseline_favorite_border_24
            }
            bindingClass.imageLikes.setImageResource(likeImage)

            bindingClass.imageShare.setOnClickListener() {
                viewModel.share() // реализация счетчика репостов
                bindingClass.textCountShare.text =
                    formatCount(post.shareCounter) // конвертирует черезё метод формат
            }

            bindingClass.imageLikes.setOnClickListener { // реализация лайка
                viewModel.like()
                bindingClass.textCountLikes.text =
                    formatCount(post.likeCounter) // конвертирует через метод формат

            }
        }
    }
}


