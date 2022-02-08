package com.hafidmust.academy.ui.bookmark

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hafidmust.academy.R
import com.hafidmust.academy.data.CourseEntity
import com.hafidmust.academy.databinding.ItemsBookmarkBinding
import com.hafidmust.academy.ui.detail.DetailCourseActivity

class BookmarkAdapter(private val callback : BookmarkFragmentCallback) : RecyclerView.Adapter<BookmarkAdapter.CourseViewHolder>() {

    private var listCourse = ArrayList<CourseEntity>()

    fun setCourse(courses : List<CourseEntity>?){
        if (courses == null) return
        this.listCourse.clear()
        this.listCourse.addAll(courses)
    }

    inner class CourseViewHolder(private val binding : ItemsBookmarkBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(course : CourseEntity){
            with(binding){
                tvItemTitle.text = course.title
                tvItemDate.text = itemView.resources.getString(R.string.deadline_date, course.deadline)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailCourseActivity::class.java)
                    intent.putExtra(DetailCourseActivity.EXTRA_COURSE, course.courseId)
                    itemView.context.startActivity(intent)
                }
                imgShare.setOnClickListener { callback.onShareClick(course) }
                Glide.with(itemView.context)
                    .load(course.imagePath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                    .into(imgPoster)
            }
        }
    }

    interface BookmarkFragmentCallback {
        fun onShareClick(course : CourseEntity)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookmarkAdapter.CourseViewHolder {
        val binding = ItemsBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkAdapter.CourseViewHolder, position: Int) {
        val course = listCourse[position]
        holder.bind(course)
    }

    override fun getItemCount(): Int = listCourse.size
}