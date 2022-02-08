package com.hafidmust.academy.ui.detail

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import com.hafidmust.academy.databinding.ActivityDetailCourseBinding

class DetailCourseActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_COURSE = "extra_course"
    }

    private lateinit var binding: ActivityDetailCourseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }


}