package com.hafidmust.academy.data.source.remote

import android.os.Handler
import android.os.Looper
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

    fun getAllCourses(callback : LoadCoursesCallback) {
        EspressoIdlingResource.increment()
        handler.postDelayed({callback.onAllCoursesReceived(jsonHelper.loadCourses())
                            EspressoIdlingResource.decrement()
                            }, SERVICE_LATENCY_IN_MILIS)
    }

    interface LoadCoursesCallback {
        fun onAllCoursesReceived(courseResponses: List<CourseResponse>)

    }

    fun getModules(courseId: String, callback : LoadModulesCallback) {
        EspressoIdlingResource.increment()
        handler.postDelayed({callback.onAllModulesReceived(jsonHelper.loadModule(courseId))
                            EspressoIdlingResource.decrement()
                            }, SERVICE_LATENCY_IN_MILIS)
    }

    interface LoadModulesCallback {
        fun onAllModulesReceived(moduleResponses : List<ModuleResponse>)
    }

    fun getContent(moduleId : String, callback: LoadContentCallback) {
        EspressoIdlingResource.increment()
        handler.postDelayed({callback.onContentReceived(jsonHelper.loadContent(moduleId))
                            EspressoIdlingResource.decrement()
                            }, SERVICE_LATENCY_IN_MILIS)
    }

    interface LoadContentCallback {
        fun onContentReceived(contentResponse : ContentResponse)
    }
}