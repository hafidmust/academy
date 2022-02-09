package com.hafidmust.academy.data.source

import com.hafidmust.academy.data.ContentEntity
import com.hafidmust.academy.data.CourseEntity
import com.hafidmust.academy.data.ModuleEntity
import com.hafidmust.academy.data.source.remote.RemoteDataSource

class FakeAcademyRepository (private val remoteDataSource: RemoteDataSource) : AcademyDataSource{


    override fun getAllCourses(): List<CourseEntity> {
        val courseResponse = remoteDataSource.getAllCourses()
        val courseList = ArrayList<CourseEntity>()
        for (response in courseResponse){
            val course = CourseEntity(
                response.id,
                response.title,
                response.description,
                response.date,
                false,
                response.imagePath
            )
            courseList.add(course)
        }
        return courseList
    }

    override fun getBookmarkedCourse(): List<CourseEntity> {
        val courseResponses = remoteDataSource.getAllCourses()
        val courseList = ArrayList<CourseEntity>()
        for ( response in courseResponses){
            val course = CourseEntity(
                response.id,
                response.title,
                response.description,
                response.date,
                false,
                response.imagePath
            )
            courseList.add(course)
        }
        return courseList
    }

    override fun getCourseWithModules(courseId: String): CourseEntity {
        val courseResponses = remoteDataSource.getAllCourses()
        lateinit var course : CourseEntity
        for (response in courseResponses){
            if (response.id == courseId){
                course = CourseEntity(
                    response.id,
                    response.title,
                    response.description,
                    response.date,
                    false,
                    response.imagePath
                )
            }
        }
        return course
    }

    override fun getAllModuleByCourse(courseId: String): List<ModuleEntity> {
        val moduleResponses = remoteDataSource.getModules(courseId)
        val moduleList = ArrayList<ModuleEntity>()
        for (response in moduleResponses){
            val course = ModuleEntity(
                response.moduleId,
                response.courseId,
                response.title,
                response.position,
                false
            )
            moduleList.add(course)
        }
        return moduleList
    }

    override fun getContent(courseId: String, moduleId: String): ModuleEntity {
        val moduleResponses = remoteDataSource.getModules(courseId)
        lateinit var module : ModuleEntity
        for (response in moduleResponses){
            module = ModuleEntity(
                response.moduleId,
                response.courseId,
                response.title,
                response.position,
                false
            )
            module.contentEntity = ContentEntity(remoteDataSource.getContent(moduleId).content)
            break
        }
        return module
    }
}