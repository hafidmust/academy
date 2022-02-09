package com.hafidmust.academy.data.source

import com.hafidmust.academy.data.CourseEntity
import com.hafidmust.academy.data.ModuleEntity

interface AcademyDataSource {
    fun getAllCourses() : List<CourseEntity>

    fun getBookmarkedCourse() : List<CourseEntity>

    fun getCourseWithModules(courseId : String) : CourseEntity

    fun getAllModuleByCourse(courseId: String) : List<ModuleEntity>

    fun getContent(courseId: String, moduleId : String) : ModuleEntity

}