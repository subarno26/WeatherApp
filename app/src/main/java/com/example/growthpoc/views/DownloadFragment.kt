package com.example.growthpoc.views

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.growthpoc.R
import com.example.growthpoc.viewmodels.DownloadViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_download.*
import javax.inject.Inject


class DownloadFragment : DaggerFragment() {
    private val TAG = "DownloadFragment"

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var downloadViewModel: DownloadViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        downloadViewModel = ViewModelProviders.of(this, factory).get(DownloadViewModel::class.java)
        return inflater.inflate(R.layout.fragment_download, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        setListeners()
        super.onActivityCreated(savedInstanceState)
    }

    private fun setListeners() {
        iv_download_pdf.setOnClickListener {
            checkPermissions()
        }
    }


    private fun startDownload() {
        downloadViewModel.downloadFile().observe(viewLifecycleOwner, { workinfo ->
            if (workinfo != null) {
                Log.d(TAG, "setObservers: ${workinfo.state.name}")
                when (workinfo.state.name) {
                    "ENQUEUED" -> {
                        Toast.makeText(context, "Enqueued, waiting for wifi", Toast.LENGTH_SHORT)
                            .show()
                    }
                    "RUNNING" -> {
                        Toast.makeText(context, "Download started", Toast.LENGTH_SHORT).show()
                    }
                    "SUCCEEDED" -> {
                        Toast.makeText(context, getString(R.string.download_completed), Toast.LENGTH_SHORT).show()
                    }
                }
//                if (workinfo.state.isFinished) {
//                    Toast.makeText(
//                        context,
//                        getString(R.string.download_completed),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
            }
        })
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context?.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 100)
            } else {
                startDownload()
            }
        } else {
            startDownload()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            100 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(context,getString(R.string.permission_granted),Toast.LENGTH_SHORT).show()
                    startDownload()
                }
                else{
                    Toast.makeText(context,getString(R.string.permission_denied),Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}