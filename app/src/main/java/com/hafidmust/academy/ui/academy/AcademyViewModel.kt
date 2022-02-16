package com.hafidmust.academy.ui.academy

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hafidmust.academy.data.source.local.entity.CourseEntity
import com.hafidmust.academy.data.source.AcademyRepository
import com.hafidmust.academy.vo.Resource

class AcademyViewModel(private val academyRepository: AcademyRepository) : ViewModel() {
    fun getCourses() : LiveData<Resource<List<CourseEntity>>> = academyRepository.getAllCourses()
}