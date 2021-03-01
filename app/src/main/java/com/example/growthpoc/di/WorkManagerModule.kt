package com.example.growthpoc.di

import android.app.Application
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WorkManagerModule {

    @Singleton
    @Provides
    fun provideWorkManager(application: Application) : WorkManager{
        return WorkManager.getInstance(application)
    }
}