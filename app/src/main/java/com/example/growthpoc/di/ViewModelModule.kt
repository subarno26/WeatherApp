package com.example.growthpoc.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.growthpoc.viewmodels.DownloadViewModel
import com.example.growthpoc.viewmodels.NetworkViewModel
import com.example.growthpoc.viewmodels.ViewModelProviderFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory)
            : ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(NetworkViewModel::class)
    abstract fun bindNetworkViewModel(networkViewModel: NetworkViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DownloadViewModel::class)
    abstract fun bindDownloadViewModel(downloadViewModel: DownloadViewModel) : ViewModel
}