package com.example.growthpoc.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.growthpoc.DownloadWorker
import javax.inject.Inject

class DownloadViewModel @Inject constructor(val workManager: WorkManager): ViewModel() {

    fun downloadFile(): LiveData<WorkInfo> {
        val workRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .addTag("downloadStatus")
            .build()

        workManager.enqueue(workRequest)
        var status = workManager.getWorkInfoByIdLiveData(workRequest.id)
        return status
    }


}