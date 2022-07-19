package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
                    Log.d("Tag", "Activity - edit")
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
        bindingClass.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            val newPost = adapter.itemCount < posts.size
            adapter.submitList(posts) {
                if (newPost) bindingClass.list.smoothScrollToPosition(0)
                Log.d("Tag", "Smooth")
            }
        }
        viewModel.edited.observe(this) { edited ->
            if (edited.id == 0L) {
                Log.d("Tag", "Edited == 0")
                return@observe
            }
            bindingClass.content.requestFocus()
            bindingClass.content.setText(edited.text)
            Log.d("Tag", "edited observe")
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
            bindingClass.content.clearFocus()
            AndroidUtils.hideKeyboard(bindingClass.content)
            bindingClass.content.setText("")
            bindingClass.group.visibility = View.GONE
            Log.d("Tag", "Click Save")
        }
        bindingClass.imageCloseEditText.setOnClickListener {
            AndroidUtils.hideKeyboard(bindingClass.content)
            bindingClass.content.setText("")
            bindingClass.group.visibility = View.GONE
            bindingClass.content.clearFocus()
            viewModel.clear()
            Log.d("Tag", "CLick close Edit")
        }
    }
}



