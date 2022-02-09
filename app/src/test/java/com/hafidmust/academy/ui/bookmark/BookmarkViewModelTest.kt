package com.hafidmust.academy.ui.bookmark

import com.hafidmust.academy.data.CourseEntity
import com.hafidmust.academy.data.source.AcademyRepository
import com.hafidmust.academy.utils.DataDummy
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BookmarkViewModelTest {

    private lateinit var viewModel: BookmarkViewModel

    @Mock
    private lateinit var academyRepository: AcademyRepository

    @Before
    fun setUp() {
        viewModel = BookmarkViewModel(academyRepository)
    }

    @Test
    fun getBookmarks() {
        Mockito.`when`(academyRepository.getBookmarkedCourse()).thenReturn(DataDummy.generateDummyCourse() as ArrayList<CourseEntity>)
        val courseEntities = viewModel.getBookmarks()
        Mockito.verify(academyRepository).getBookmarkedCourse()
        assertNotNull(courseEntities)
        assertEquals(5, courseEntities.size)
    }
}