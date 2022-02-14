package com.hafidmust.academy.data.source

import androidx.lifecycle.LiveData
import com.hafidmust.academy.data.source.local.entity.CourseEntity
import com.hafidmust.academy.data.ModuleEntity

interface AcademyDataSource {
    fun getAllCourses() : LiveData<List<CourseEntity>>

    fun getBookmarkedCourse() : LiveData<List<CourseEntity>>

    fun getCourseWithModules(courseId : String) : LiveData<CourseEntity>

    fun getAllModuleByCourse(courseId: String) : LiveData<List<ModuleEntity>>

    fun getContent(courseId: String, moduleId : String) : LiveData<ModuleEntity>

}