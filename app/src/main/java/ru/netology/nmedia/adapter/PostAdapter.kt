package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.formatCount
import java.security.Provider

interface PostEventListener {
    fun edit(post: Post)
    fun onRemove(post: Post)
    fun like(post: Post)
    fun onShare(post: Post)
}// создаем слушателя

class PostAdapter(
    private val listener: PostEventListener
) : ListAdapter<Post, PostAdapter.PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, listener = listener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

    class PostViewHolder(
        private val binding: CardPostBinding,
        private val listener: PostEventListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.apply {
                titleAuthor.text = post.author
                timePosts.text = post.time
                textContent.text = post.text
                textCountLikes.text = formatCount(post.likeCounter)
                textCountShare.text = formatCount(post.shareCounter)
                if (post.liked) {
                    imageLikes.setImageResource(R.drawable.ic_baseline_full_favorite_24)
                } else {
                    imageLikes.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                }
                imageLikes.setOnClickListener { listener.like(post) }
                imageShare.setOnClickListener { listener.onShare(post) }
                buttonMenu.setOnClickListener {
                    PopupMenu(it.context, it).apply {
                        inflate(R.menu.post_menu) // раздуть айтем меню
                        setOnMenuItemClickListener { menuItem -> // обработать нажатие
                            when (menuItem.itemId) {
                                R.id.remove -> {
                                    listener.onRemove(post)
                                    return@setOnMenuItemClickListener true
                                }
                                R.id.edit -> {
                                    listener.edit(post)
                                    return@setOnMenuItemClickListener true
                                }
                            }
                            false
                        }
                        show()
                    }
                }
            }
        }
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

}