
package com.example.growthpoc.helper

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.growthpoc.utils.Constants

class DownloadWorker(context: Context, workerParams: WorkerParameters)
    : Worker(context, workerParams)
{
    override fun doWork(): Result {
        downloadFile()
        return Result.success()
    }

    private fun downloadFile() {
        val request = DownloadManager.Request(
            Uri.parse(Constants.DOWNLOAD_URL))
            .setTitle("Weather Forecast PDF")
            .setDescription("NASA Weather Forecast PDF")
            .setAllowedOverMetered(true)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"${System.currentTimeMillis()}.mp3")
        val dm = applicationContext.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        dm.enqueue(request)
    }
}