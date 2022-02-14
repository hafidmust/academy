package com.hafidmust.academy.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hafidmust.academy.data.source.local.entity.CourseEntity
import com.hafidmust.academy.data.source.AcademyRepository

class BookmarkViewModel(private val academyRepository: AcademyRepository) : ViewModel() {
    fun getBookmarks() : LiveData<List<CourseEntity>> = academyRepository.getBookmarkedCourse()
}