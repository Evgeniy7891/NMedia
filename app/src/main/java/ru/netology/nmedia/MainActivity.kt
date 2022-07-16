package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.adapter.PostEventListener
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        val viewModel by viewModels<PostViewModel>() // получить допуск к вьюМодели
        val adapter = PostAdapter(
            object : PostEventListener {
                override fun edit(post: Post) {
                    viewModel.edit(post)
                    bindingClass.group.visibility = View.VISIBLE
                    bindingClass.exampleTest.text = post.text
                }

                override fun onRemove(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun like(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onShare(post: Post) {
                    viewModel.shareById(post.id)
                }
            }
        )
        viewModel.edited.observe(this) { edited ->
            if (edited.id == 0L) {
                return@observe
            }
            bindingClass.content.setText(edited.text)
            bindingClass.content.requestFocus()
        }
        bindingClass.save.setOnClickListener {
            if (bindingClass.content.text.isNullOrBlank()) {
                Toast.makeText(it.context, "Post is blank", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            val text = bindingClass.content.text.toString()
            viewModel.editContent(text)
            viewModel.save()
            bindingClass.group.visibility = View.INVISIBLE
            bindingClass.content.clearFocus()
            AndroidUtils.hideKeyboard(bindingClass.content)
            bindingClass.content.setText("")
        }
        bindingClass.imageCloseEditText.setOnClickListener {
            bindingClass.group.visibility = View.INVISIBLE
            bindingClass.content.clearFocus()
            AndroidUtils.hideKeyboard(bindingClass.content)
            bindingClass.content.setText("")
        }
        bindingClass.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
    }
}



