package com.hafidmust.academy.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hafidmust.academy.data.source.remote.RemoteDataSource
import com.hafidmust.academy.utils.DataDummy
import com.hafidmust.academy.utils.LiveDataTestUtil
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito


class AcademyRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val academyRepository = FakeAcademyRepository(remote)

    private val courseResponses = DataDummy.generateRemoteDummyCourses()
    private val courseId = courseResponses[0].id
    private val moduleResponse = DataDummy.generateRemoteDummyModules(courseId)
    private val moduleId = moduleResponse[0].moduleId
    private val content = DataDummy.generateRemoteDummyContent(moduleId)

    @Test
    fun getAllCourses(){
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadCoursesCallback)
                .onAllCoursesReceived(courseResponses)
            null
        }.`when`(remote).getAllCourses(any())
        val courseEntities = LiveDataTestUtil.getValue(academyRepository.getAllCourses())
        verify(remote).getAllCourses(any())
        assertNotNull(courseEntities)
        assertEquals(courseResponses.size.toLong(), courseEntities.size.toLong())
    }

    @Test
    fun getAllModulesByCourse(){
        doAnswer {invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadModulesCallback)
                .onAllModulesReceived(moduleResponse)
            null
        }.`when`(remote).getModules(eq(courseId), any())
        val moduleEntities = LiveDataTestUtil.getValue(academyRepository.getAllModuleByCourse(courseId))
        verify(remote).getModules(eq(courseId), any())
        assertNotNull(moduleEntities)
        assertEquals(moduleResponse.size.toLong(), moduleEntities.size.toLong())
    }
    @Test
    fun getBookmarkedCourses() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadCoursesCallback)
                .onAllCoursesReceived(courseResponses)
            null
        }.`when`(remote).getAllCourses(any())
        val courseEntities = LiveDataTestUtil.getValue(academyRepository.getBookmarkedCourse())
        verify<RemoteDataSource>(remote).getAllCourses(any())
        assertNotNull(courseEntities)
        assertEquals(courseResponses.size.toLong(), courseEntities.size.toLong())
    }

    @Test
    fun getContent() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadModulesCallback)
                .onAllModulesReceived(moduleResponse)
            null
        }.`when`(remote).getModules(eq(courseId), any())

        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadContentCallback)
                .onContentReceived(content)
            null
        }.`when`(remote).getContent(eq(moduleId), any())
        val resultModule = LiveDataTestUtil.getValue(academyRepository.getContent(courseId,moduleId))
        verify(remote).getModules(eq(courseId), any())
        verify(remote).getContent(eq(moduleId), any())

        assertNotNull(resultModule)
        assertEquals(content.content, resultModule.contentEntity?.content)
    }

    @Test
    fun getCourseWithModules() {
        doAnswer {
            invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadCoursesCallback)
                .onAllCoursesReceived(courseResponses)
            null
        }.`when`(remote).getAllCourses(any())
        val resultCourse = LiveDataTestUtil.getValue(academyRepository.getCourseWithModules(courseId))
        verify<RemoteDataSource>(remote).getAllCourses(any())
        assertNotNull(resultCourse)
        assertEquals(courseResponses[0].title, resultCourse.title)
    }
}