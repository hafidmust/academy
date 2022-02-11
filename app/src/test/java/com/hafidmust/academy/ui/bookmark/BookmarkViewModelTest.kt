package com.hafidmust.academy.ui.bookmark

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.hafidmust.academy.data.CourseEntity
import com.hafidmust.academy.data.source.AcademyRepository
import com.hafidmust.academy.utils.DataDummy
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BookmarkViewModelTest {

    private lateinit var viewModel: BookmarkViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var academyRepository: AcademyRepository

    @Mock
    private lateinit var observer: Observer<List<CourseEntity>>

    @Before
    fun setUp() {
        viewModel = BookmarkViewModel(academyRepository)
    }

    @Test
    fun getBookmarks() {
        val dummyCourses = DataDummy.generateDummyCourse()
        val courses = MutableLiveData<List<CourseEntity>>()
        courses.value = dummyCourses

        Mockito.`when`(academyRepository.getBookmarkedCourse()).thenReturn(courses)
        val courseEntities = viewModel.getBookmarks().value
        Mockito.verify(academyRepository).getBookmarkedCourse()
        assertNotNull(courseEntities)
        assertEquals(5, courseEntities?.size)

        viewModel.getBookmarks().observeForever(observer)
        verify(observer).onChanged(dummyCourses)
    }
}