package com.hafidmust.academy.ui.detail

import com.hafidmust.academy.data.source.AcademyRepository
import com.hafidmust.academy.utils.DataDummy
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailCourseViewModelTest {
    private lateinit var viewModel: DetailCourseViewModel
    private val dummyCourse = DataDummy.generateDummyCourse()[0]
    private val courseId = dummyCourse.courseId

    @Mock
    private lateinit var academyRepository: AcademyRepository

    @Before
    fun setUp() {
        viewModel = DetailCourseViewModel(academyRepository)
        viewModel.setSelectedCourse(courseId)
    }

    @Test
    fun getCourse() {
        `when`(academyRepository.getCourseWithModules(courseId)).thenReturn(dummyCourse)
        val courseEntity = viewModel.getCourse()
        verify(academyRepository).getCourseWithModules(courseId)
        assertNotNull(courseEntity)
        assertEquals(dummyCourse.courseId, courseEntity.courseId)
    }

    @Test
    fun getModules() {
        `when`(academyRepository.getAllModuleByCourse(courseId)).thenReturn(DataDummy.generateDummyModules(courseId))
        val moduleEntites = viewModel.getModules()
        verify(academyRepository).getAllModuleByCourse(courseId)
        assertNotNull(moduleEntites)
        assertEquals(7, moduleEntites.size)
    }
}