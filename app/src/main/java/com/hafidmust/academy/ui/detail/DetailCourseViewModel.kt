package com.hafidmust.academy.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.hafidmust.academy.data.source.AcademyRepository
import com.hafidmust.academy.data.source.local.entity.CourseWithModule
import com.hafidmust.academy.vo.Resource

class DetailCourseViewModel(private val academyRepository: AcademyRepository) : ViewModel() {
    val courseId = MutableLiveData<String>()

    fun setSelectedCourse(courseId : String){
        this.courseId.value = courseId
    }

    var courseModule: LiveData<Resource<CourseWithModule>> = Transformations.switchMap(courseId){courseId ->
        academyRepository.getCourseWithModules(courseId)
    }

    fun setBookmark(){
        val moduleResouse = courseModule.value
        if (moduleResouse != null){
            val courseWithModule = moduleResouse.data
            if (courseWithModule != null){
                val courseEntity = courseWithModule.mCourse
                val newState = !courseEntity.bookmarked
                academyRepository.setCourseBookMark(courseEntity, newState)
            }
        }
    }
}