package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.launch
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewModel.PostViewModel


class FeedFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)

        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)


        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onEdit(post: Post) {
                viewModel.edit(post)
                val text = post.content
                val bundle = Bundle()
                bundle.getString("editedText", text)
                findNavController().navigate(R.id.action_feedFragment_to_editPostFragment, bundle)

            }

            override fun onSinglePost(post: Post) {

            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }

                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
                viewModel.shareById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onPlayVideo(post: Post) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.videoUrl))
                startActivity(intent)
            }

        })

        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }


        binding.add.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }





        return binding.root
    }
}
