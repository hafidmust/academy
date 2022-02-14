package com.hafidmust.academy.data.source.remote

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hafidmust.academy.data.source.remote.response.ContentResponse
import com.hafidmust.academy.data.source.remote.response.CourseResponse
import com.hafidmust.academy.data.source.remote.response.ModuleResponse
import com.hafidmust.academy.utils.EspressoIdlingResource
import com.hafidmust.academy.utils.JsonHelper


class RemoteDataSource private constructor(private val jsonHelper: JsonHelper){

    private val handler = Handler(Looper.getMainLooper())

    companion object{
        private const val SERVICE_LATENCY_IN_MILIS : Long = 2000

        @Volatile
        private var instance: RemoteDataSource? = null
        fun getInstance(helper: JsonHelper) : RemoteDataSource =
            instance ?: synchronized(this){
                instance ?: RemoteDataSource(helper).apply {
                    instance = this
                }
            }
    }

    fun getAllCourses() : LiveData<ApiResponse<List<CourseResponse>>> {
        EspressoIdlingResource.increment()
        val resultCourse = MutableLiveData<ApiResponse<List<CourseResponse>>>()
        handler.postDelayed({
            resultCourse.value = ApiResponse.success(jsonHelper.loadCourses())
                            EspressoIdlingResource.decrement()
                            }, SERVICE_LATENCY_IN_MILIS)
        return resultCourse
    }

    interface LoadCoursesCallback {
        fun onAllCoursesReceived(courseResponses: List<CourseResponse>)

    }

    fun getModules(courseId: String): LiveData<ApiResponse<List<ModuleResponse>>> {
        EspressoIdlingResource.increment()
        val resultModules = MutableLiveData<ApiResponse<List<ModuleResponse>>>()
        handler.postDelayed({
            resultModules.value = ApiResponse.success(jsonHelper.loadModule(courseId))
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILIS)
        return resultModules
    }

    interface LoadModulesCallback {
        fun onAllModulesReceived(moduleResponses : List<ModuleResponse>)
    }

    fun getContent(moduleId: String): LiveData<ApiResponse<ContentResponse>> {
        EspressoIdlingResource.increment()
        val resultContent = MutableLiveData<ApiResponse<ContentResponse>>()
        handler.postDelayed({
            resultContent.value = ApiResponse.success(jsonHelper.loadContent(moduleId))
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILIS)
        return resultContent
    }

    interface LoadContentCallback {
        fun onContentReceived(contentResponse : ContentResponse)
    }
}