package com.hafidmust.academy.di

import android.content.Context
import com.hafidmust.academy.data.source.AcademyRepository
import com.hafidmust.academy.data.source.remote.RemoteDataSource
import com.hafidmust.academy.utils.JsonHelper

object Injection {
    fun provideRepository(context : Context) : AcademyRepository{
        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))
        return AcademyRepository.getInstance(remoteDataSource)
    }
}