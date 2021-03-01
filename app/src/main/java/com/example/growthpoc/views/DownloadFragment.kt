package com.example.growthpoc.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.growthpoc.R
import com.example.growthpoc.viewmodels.DownloadViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class DownloadFragment : DaggerFragment() {
    private val TAG = "DownloadFragment"
    @Inject
    lateinit var factory : ViewModelProvider.Factory
    private lateinit var downloadViewModel: DownloadViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        downloadViewModel = ViewModelProviders.of(this,factory).get(DownloadViewModel::class.java)
        return inflater.inflate(R.layout.fragment_download, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
    }

    private fun setObservers() {
        downloadViewModel.downloadFile().observe(viewLifecycleOwner, Observer { workinfo ->
            if (workinfo != null) {
                Log.d(TAG, "setObservers: ${workinfo.state.name}")
            }
        })
    }

}