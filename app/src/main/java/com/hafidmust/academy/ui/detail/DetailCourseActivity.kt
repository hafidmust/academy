package com.hafidmust.academy.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.hafidmust.academy.R
import com.hafidmust.academy.data.source.local.entity.CourseEntity

import com.hafidmust.academy.databinding.ActivityDetailCourseBinding
import com.hafidmust.academy.databinding.ContentDetailCourseBinding
import com.hafidmust.academy.ui.reader.CourseReaderActivity
import com.hafidmust.academy.viewmodel.ViewModelFactory

class DetailCourseActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_COURSE = "extra_course"
    }

    private lateinit var detailContentBinding: ContentDetailCourseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityDetailCourseBinding = ActivityDetailCourseBinding.inflate(layoutInflater)
        detailContentBinding = activityDetailCourseBinding.detailContent
        setContentView(activityDetailCourseBinding.root)

        setSupportActionBar(activityDetailCourseBinding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[DetailCourseViewModel::class.java]

        val adapter = DetailCourseAdapter()
        val extras = intent.extras
        if (extras != null){
            val courseId = extras.getString(EXTRA_COURSE)
            if (courseId != null){
                activityDetailCourseBinding.progressBar.visibility = View.VISIBLE
                activityDetailCourseBinding.content.visibility = View.INVISIBLE

                viewModel.setSelectedCourse(courseId)
                viewModel.getModules().observe(this){
                    activityDetailCourseBinding.progressBar.visibility = View.GONE
                    activityDetailCourseBinding.content.visibility = View.VISIBLE

                    adapter.setModules(it)
                    adapter.notifyDataSetChanged()

                }

                viewModel.getCourse().observe(this){
                    populateCourse(it)
                }
            }
        }

        with(detailContentBinding.rvModule){
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(this@DetailCourseActivity)
            setHasFixedSize(true)
            this.adapter = adapter
            val dividerItemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }

    }

    private fun populateCourse(courseEntity: CourseEntity) {
        detailContentBinding.textTitle.text = courseEntity.title
        detailContentBinding.textDescription.text = courseEntity.description
        detailContentBinding.textDate.text = resources.getString(R.string.deadline_date, courseEntity.deadline)

        Glide.with(this)
            .load(courseEntity.imagePath)
            .transform(RoundedCorners(20))
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                .error(R.drawable.ic_error))
            .into(detailContentBinding.imagePoster)

        detailContentBinding.btnStart.setOnClickListener {
            val intent = Intent(this@DetailCourseActivity, CourseReaderActivity::class.java)
            intent.putExtra(CourseReaderActivity.EXTRA_COURSE_ID, courseEntity.courseId)
            startActivity(intent)
        }
    }


}