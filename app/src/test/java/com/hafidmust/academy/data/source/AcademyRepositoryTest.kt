package com.hafidmust.academy.data.source

import com.hafidmust.academy.data.source.remote.RemoteDataSource
import com.hafidmust.academy.data.source.remote.response.ContentResponse
import com.hafidmust.academy.data.source.remote.response.CourseResponse
import com.hafidmust.academy.data.source.remote.response.ModuleResponse
import com.hafidmust.academy.utils.DataDummy
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class AcademyRepositoryTest {

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val academyRepository = FakeAcademyRepository(remote)

    private val courseResponses = DataDummy.generateRemoteDummyCourses()
    private val courseId = courseResponses[0].id
    private val moduleResponse = DataDummy.generateRemoteDummyModules(courseId)
    private val moduleId = moduleResponse[0].moduleId
    private val content = DataDummy.generateRemoteDummyContent(moduleId)

    @Test
    fun getAllCourses(){
        `when`(remote.getAllCourses()).thenReturn(courseResponses)
        val courseEntities = academyRepository.getAllCourses()
        verify(remote).getAllCourses()
        assertNotNull(courseEntities)
        assertEquals(courseResponses.size.toLong(), courseEntities.size.toLong())
    }

    @Test
    fun getAllModulesByCourse(){
        `when`(remote.getModules(courseId)).thenReturn(moduleResponse)
        val moduleEntities = academyRepository.getAllModuleByCourse(courseId)
        verify(remote).getModules(courseId)
        assertNotNull(moduleEntities)
        assertEquals(moduleResponse.size.toLong(), moduleEntities.size.toLong())
    }
    @Test
    fun getBookmarkedCourses() {
        `when`<List<CourseResponse>>(remote.getAllCourses()).thenReturn(courseResponses)
        val courseEntities = academyRepository.getBookmarkedCourse()
        verify<RemoteDataSource>(remote).getAllCourses()
        assertNotNull(courseEntities)
        assertEquals(courseResponses.size.toLong(), courseEntities.size.toLong())
    }

    @Test
    fun getContent() {
        `when`<List<ModuleResponse>>(remote.getModules(courseId)).thenReturn(moduleResponse)
        `when`<ContentResponse>(remote.getContent(moduleId)).thenReturn(content)
        val resultModule = academyRepository.getContent(courseId, moduleId)
        verify<RemoteDataSource>(remote).getContent(moduleId)
        assertNotNull(resultModule)
        assertEquals(content.content, resultModule.contentEntity?.content)
    }

    @Test
    fun getCourseWithModules() {
        `when`<List<CourseResponse>>(remote.getAllCourses()).thenReturn(courseResponses)
        val resultCourse = academyRepository.getCourseWithModules(courseId)
        verify<RemoteDataSource>(remote).getAllCourses()
        assertNotNull(resultCourse)
        assertEquals(courseResponses[0].title, resultCourse.title)
    }
}