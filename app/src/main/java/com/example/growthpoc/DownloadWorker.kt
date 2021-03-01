package com.example.growthpoc

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
    private val TAG = "DownloadWorker"
    override fun doWork(): Result {
        downloadVideoFile()
        return Result.success()
    }

    private fun downloadVideoFile() {
        var request = DownloadManager.Request(
            Uri.parse("https://www.videezy.com/download/40410?download_auth_hash=9dd4321b&pro=false"))
            .setTitle("Weather Forecast PDF")
            .setDescription("All India Weather Forecast Bulletin")
            .setAllowedOverMetered(true)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"${System.currentTimeMillis()}")
        var dm = applicationContext.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        dm.enqueue(request)
    }
}