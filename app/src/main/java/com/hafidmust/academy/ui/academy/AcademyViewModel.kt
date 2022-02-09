package com.hafidmust.academy.ui.academy

import androidx.lifecycle.ViewModel
import com.hafidmust.academy.data.CourseEntity
import com.hafidmust.academy.utils.DataDummy

class AcademyViewModel : ViewModel() {
    fun getCourses() : List<CourseEntity> = DataDummy.generateDummyCourse()
}