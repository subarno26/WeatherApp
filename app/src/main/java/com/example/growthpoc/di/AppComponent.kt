package com.example.growthpoc.di

import android.app.Application
import com.example.growthpoc.BaseApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class,
    ActivityBuilderModule::class,
    NetworkModule::class, ContextModule::class,
    WorkManagerModule::class])
interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application): Builder

        fun build() : AppComponent
    }
}