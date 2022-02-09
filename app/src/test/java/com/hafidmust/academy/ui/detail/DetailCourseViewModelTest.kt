package com.hafidmust.academy.ui.detail

import com.hafidmust.academy.utils.DataDummy
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class DetailCourseViewModelTest {
    private lateinit var viewModel: DetailCourseViewModel
    private val dummyCourse = DataDummy.generateDummyCourse()[0]
    private val courseId = dummyCourse.courseId

    @Before
    fun setUp() {
        viewModel = DetailCourseViewModel()
        viewModel.setSelectedCourse(courseId)
    }

    @Test
    fun getCourse() {
        viewModel.setSelectedCourse(dummyCourse.courseId)
        val courseEntity = viewModel.getCourse()
        assertNotNull(courseEntity)
        assertEquals(dummyCourse.courseId, courseEntity.courseId)
    }

    @Test
    fun getModules() {
        val moduleEntites = viewModel.getModules()
        assertNotNull(moduleEntites)
        assertEquals(7, moduleEntites.size)
    }
}