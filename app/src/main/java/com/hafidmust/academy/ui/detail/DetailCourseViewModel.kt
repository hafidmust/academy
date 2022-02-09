package com.hafidmust.academy.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hafidmust.academy.data.CourseEntity
import com.hafidmust.academy.data.ModuleEntity
import com.hafidmust.academy.data.source.AcademyRepository
import com.hafidmust.academy.utils.DataDummy

class DetailCourseViewModel(private val academyRepository: AcademyRepository) : ViewModel() {
    private lateinit var courseId : String

    fun setSelectedCourse(courseId : String){
        this.courseId = courseId
    }

    fun getCourse() : LiveData<CourseEntity> = academyRepository.getCourseWithModules(courseId)

    fun getModules() : LiveData<List<ModuleEntity>> = academyRepository.getAllModuleByCourse(courseId)
}