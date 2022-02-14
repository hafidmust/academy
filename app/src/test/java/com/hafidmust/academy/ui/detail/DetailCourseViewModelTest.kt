package com.hafidmust.academy.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.hafidmust.academy.data.source.local.entity.CourseEntity
import com.hafidmust.academy.data.ModuleEntity
import com.hafidmust.academy.data.source.AcademyRepository
import com.hafidmust.academy.utils.DataDummy
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
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
    private val dummyModules = DataDummy.generateDummyModules(courseId)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var academyRepository: AcademyRepository

    @Mock
    private lateinit var courseObserver: Observer<CourseEntity>

    @Mock
    private lateinit var moduleObserver: Observer<List<ModuleEntity>>

    @Before
    fun setUp() {
        viewModel = DetailCourseViewModel(academyRepository)
        viewModel.setSelectedCourse(courseId)
    }

    @Test
    fun getCourse() {
        val courses = MutableLiveData<CourseEntity>()
        courses.value = dummyCourse

        `when`(academyRepository.getCourseWithModules(courseId)).thenReturn(courses)
        val courseEntity = viewModel.getCourse().value
        verify(academyRepository).getCourseWithModules(courseId)
        assertNotNull(courseEntity)
        assertEquals(dummyCourse.courseId, courseEntity?.courseId)
        viewModel.getCourse().observeForever(courseObserver)
        verify(courseObserver).onChanged(dummyCourse)
    }

    @Test
    fun getModules() {
        val modules = MutableLiveData<List<ModuleEntity>>()
        modules.value = dummyModules
        `when`(academyRepository.getAllModuleByCourse(courseId)).thenReturn(modules)
        val moduleEntites = viewModel.getModules().value
        verify(academyRepository).getAllModuleByCourse(courseId)
        assertNotNull(moduleEntites)
        assertEquals(7, moduleEntites?.size)

        viewModel.getModules().observeForever(moduleObserver)
        verify(moduleObserver).onChanged(dummyModules)
    }
}