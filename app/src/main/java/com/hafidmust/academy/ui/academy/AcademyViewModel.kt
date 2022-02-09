package com.hafidmust.academy.ui.academy

import androidx.lifecycle.ViewModel
import com.hafidmust.academy.data.CourseEntity
import com.hafidmust.academy.data.source.AcademyRepository
import com.hafidmust.academy.utils.DataDummy

class AcademyViewModel(private val academyRepository: AcademyRepository) : ViewModel() {
    fun getCourses() : List<CourseEntity> = academyRepository.getAllCourses()
}