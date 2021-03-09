package com.example.growthpoc.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.work.*
import com.example.growthpoc.helper.DownloadWorker
import javax.inject.Inject

class DownloadViewModel @Inject constructor(private val workManager: WorkManager): ViewModel() {

    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    fun downloadFile(): LiveData<WorkInfo> {
        val workRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .addTag("downloadStatus")
            .setConstraints(constraints)
            .build()

        workManager.enqueue(workRequest)
        return workManager.getWorkInfoByIdLiveData(workRequest.id)
    }


}