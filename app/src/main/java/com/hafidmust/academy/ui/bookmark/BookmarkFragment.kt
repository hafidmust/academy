package com.hafidmust.academy.ui.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hafidmust.academy.R
import com.hafidmust.academy.data.source.local.entity.CourseEntity
import com.hafidmust.academy.databinding.FragmentBookmarkBinding
import com.hafidmust.academy.viewmodel.ViewModelFactory


class BookmarkFragment : Fragment(), BookmarkAdapter.BookmarkFragmentCallback {

    private lateinit var binding : FragmentBookmarkBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null){
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[BookmarkViewModel::class.java]


            val adapter = BookmarkAdapter(this)
            binding.progressBar.visibility = View.VISIBLE
            viewModel.getBookmarks().observe(viewLifecycleOwner){
                binding.progressBar.visibility = View.GONE
                adapter.setCourse(it)
                adapter.notifyDataSetChanged()
            }

            with(binding.rvBookmark){
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                this.adapter = adapter
            }
        }
    }

    override fun onShareClick(course: CourseEntity) {
        if (activity != null){
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder
                .from(requireActivity())
                .setType(mimeType)
                .setChooserTitle("Bagikan aplikasi sekarang")
                .setText(resources.getString(R.string.share_text, course.title))
                .startChooser()
        }
    }

}