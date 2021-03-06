package com.hafidmust.academy.data.source

import androidx.lifecycle.LiveData
import com.hafidmust.academy.data.source.local.entity.CourseEntity
import com.hafidmust.academy.data.source.local.entity.CourseWithModule
import com.hafidmust.academy.data.source.local.entity.ModuleEntity
import com.hafidmust.academy.vo.Resource

interface AcademyDataSource {
    fun getAllCourses() : LiveData<Resource<List<CourseEntity>>>

    fun getBookmarkedCourse() : LiveData<List<CourseEntity>>

    fun getCourseWithModules(courseId : String) : LiveData<Resource<CourseWithModule>>

    fun getAllModuleByCourse(courseId: String) : LiveData<Resource<List<ModuleEntity>>>

    fun getContent(moduleId : String) : LiveData<Resource<ModuleEntity>>

    fun setCourseBookMark(course : CourseEntity, state : Boolean)

    fun setReadModule(module : ModuleEntity)

}