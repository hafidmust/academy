package com.hafidmust.academy.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hafidmust.academy.data.CourseEntity
import com.hafidmust.academy.data.source.AcademyRepository
import com.hafidmust.academy.utils.DataDummy

class BookmarkViewModel(private val academyRepository: AcademyRepository) : ViewModel() {
    fun getBookmarks() : LiveData<List<CourseEntity>> = academyRepository.getBookmarkedCourse()
}