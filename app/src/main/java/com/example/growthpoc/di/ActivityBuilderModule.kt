package com.example.growthpoc.di

import com.example.growthpoc.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [
        ViewModelModule::class,
        FragmentBuilderModule::class])
    abstract fun contributeMainActivity() : MainActivity
}