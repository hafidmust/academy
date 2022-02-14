package com.hafidmust.academy.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hafidmust.academy.data.source.local.entity.ContentEntity
import com.hafidmust.academy.data.source.local.entity.CourseEntity
import com.hafidmust.academy.data.ModuleEntity
import com.hafidmust.academy.data.source.remote.RemoteDataSource
import com.hafidmust.academy.data.source.remote.response.ContentResponse
import com.hafidmust.academy.data.source.remote.response.CourseResponse
import com.hafidmust.academy.data.source.remote.response.ModuleResponse

class FakeAcademyRepository(private val remoteDataSource: RemoteDataSource) : AcademyDataSource{



    override fun getAllCourses(): LiveData<List<CourseEntity>> {
        val courseResults = MutableLiveData<List<CourseEntity>>()
        remoteDataSource.getAllCourses(object : RemoteDataSource.LoadCoursesCallback{
            override fun onAllCoursesReceived(courseResponses: List<CourseResponse>) {
                val courseList = ArrayList<CourseEntity>()
                for (response in courseResponses){
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
                courseResults.postValue(courseList)
            }
        })
        return courseResults
    }

    override fun getBookmarkedCourse(): LiveData<List<CourseEntity>> {
        val courseResults = MutableLiveData<List<CourseEntity>>()
        remoteDataSource.getAllCourses(object : RemoteDataSource.LoadCoursesCallback{
            override fun onAllCoursesReceived(courseResponses: List<CourseResponse>) {
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
                courseResults.postValue(courseList)
            }
        })

        return courseResults
    }

    override fun getCourseWithModules(courseId: String): LiveData<CourseEntity> {
        val courseResults = MutableLiveData<CourseEntity>()
        remoteDataSource.getAllCourses(object : RemoteDataSource.LoadCoursesCallback{
            override fun onAllCoursesReceived(courseResponses: List<CourseResponse>) {
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
                courseResults.postValue(course)
            }
        })

        return courseResults
    }

    override fun getAllModuleByCourse(courseId: String): LiveData<List<ModuleEntity>> {
        val moduleResults = MutableLiveData<List<ModuleEntity>>()
        remoteDataSource.getModules(courseId, object : RemoteDataSource.LoadModulesCallback{
            override fun onAllModulesReceived(moduleResponses: List<ModuleResponse>) {
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
                moduleResults.postValue(moduleList)
            }
        })


        return moduleResults
    }

    override fun getContent(courseId: String, moduleId: String): LiveData<ModuleEntity> {
        val moduleResults = MutableLiveData<ModuleEntity>()
        remoteDataSource.getModules(courseId, object : RemoteDataSource.LoadModulesCallback{
            override fun onAllModulesReceived(moduleResponses: List<ModuleResponse>) {
                lateinit var module : ModuleEntity
                for (response in moduleResponses){
                    module = ModuleEntity(
                        response.moduleId,
                        response.courseId,
                        response.title,
                        response.position,
                        false
                    )
                    remoteDataSource.getContent(moduleId, object : RemoteDataSource.LoadContentCallback{
                        override fun onContentReceived(contentResponse: ContentResponse) {
                            module.contentEntity = ContentEntity(contentResponse.content)
                            moduleResults.postValue(module)
                        }
                    })
                    break
                }
            }
        })

        return moduleResults
    }
}