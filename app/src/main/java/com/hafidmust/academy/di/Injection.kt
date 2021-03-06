package com.hafidmust.academy.di

import android.content.Context
import com.hafidmust.academy.data.source.AcademyRepository
import com.hafidmust.academy.data.source.local.LocalDataSource
import com.hafidmust.academy.data.source.local.room.AcademyDatabase
import com.hafidmust.academy.data.source.remote.RemoteDataSource
import com.hafidmust.academy.utils.AppExecutors
import com.hafidmust.academy.utils.JsonHelper

object Injection {
    fun provideRepository(context : Context) : AcademyRepository{
        val database = AcademyDatabase.getInstance(context)
        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))
        val localDataSource = LocalDataSource.getInstance(database.academyDao())
        val appExecutors = AppExecutors()

        return AcademyRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}