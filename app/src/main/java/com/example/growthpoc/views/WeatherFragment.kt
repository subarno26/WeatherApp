package com.example.growthpoc.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.growthpoc.R
import com.example.growthpoc.viewmodels.NetworkViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_weather.*
import javax.inject.Inject

class WeatherFragment : DaggerFragment() {
    private val TAG = "WeatherFragment"
    @Inject
    lateinit var factory : ViewModelProvider.Factory

    private lateinit var networkViewModel: NetworkViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_weather, container, false)
        networkViewModel = ViewModelProviders.of(this,factory).get(NetworkViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        populateViews()
        setListeners()
    }

    private fun setListeners() {
        btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_weatherFragment_to_downloadFragment)
        }
    }

    private fun populateViews() {
        networkViewModel.temp.observe(viewLifecycleOwner, {
            Log.d(TAG,it.toString())
        })
        networkViewModel.pressure.observe(viewLifecycleOwner, Observer {
            Log.d(TAG,it.toString())
        })
        networkViewModel.humidity.observe(viewLifecycleOwner, Observer {
            Log.d(TAG,it.toString())
        })
        networkViewModel.temp_min.observe(viewLifecycleOwner, Observer {
            Log.d(TAG,it.toString())
        })
        networkViewModel.temp_max.observe(viewLifecycleOwner, Observer {
            Log.d(TAG,it.toString())
        })
        networkViewModel.main.observe(viewLifecycleOwner, Observer {
            Log.d(TAG,it.toString())
        })
        networkViewModel.description.observe(viewLifecycleOwner, Observer {
            Log.d(TAG,it.toString())
        })
        networkViewModel.icon.observe(viewLifecycleOwner, Observer {
            Log.d(TAG,it.toString())
        })

    }

    private fun setObservers() {
        networkViewModel.getResponse().observe(viewLifecycleOwner, Observer {
            Log.d(TAG,it.toString())
        })
    }
}