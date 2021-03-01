package com.example.growthpoc.di

import com.example.growthpoc.views.DownloadFragment
import com.example.growthpoc.views.WeatherFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeWeatherFragment() : WeatherFragment

    @ContributesAndroidInjector
    abstract fun contributeDownloadFragment() : DownloadFragment
}