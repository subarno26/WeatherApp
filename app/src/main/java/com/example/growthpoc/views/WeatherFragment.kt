package com.example.growthpoc.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.growthpoc.R
import com.example.growthpoc.databinding.FragmentWeatherBinding
import com.example.growthpoc.utils.Constants
import com.example.growthpoc.viewmodels.NetworkViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_weather.*
import javax.inject.Inject

class WeatherFragment : DaggerFragment() {
    val args: WeatherFragmentArgs by navArgs()
    private val TAG = "WeatherFragment"
    private lateinit var binding: FragmentWeatherBinding
    private var cityName = ""

//    @Inject
//    lateinit var factory: ViewModelProvider.Factory

    @Inject
    lateinit var networkViewModel: NetworkViewModel

//    private lateinit var networkViewModel: NetworkViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_weather,container,false)
//        networkViewModel = ViewModelProviders.of(this, factory).get(NetworkViewModel::class.java)
        cityName = args.cityName
        return binding.root
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindData()
        networkViewModel.checkNetworkStatus()
        setObservers()
        setListeners()

    }

    private fun bindData() {
//        networkViewModel = ViewModelProviders.of(this, factory).get(NetworkViewModel::class.java)
        binding.viewModel = networkViewModel
        binding.lifecycleOwner = this
    }

    private fun setObservers() {
        networkViewModel.isConnected.observe(viewLifecycleOwner, { it ->
//            Log.d(TAG, "setObservers: ${it.toString()}")
            if (it) {
                callApi(cityName)
                networkViewModel.myResponse.observe(viewLifecycleOwner, {
                    if (it != null) {
                        setIcons(it.weather[0].icon)
                    } else {
                        Toast.makeText(
                            context,
                            getString(R.string.wrong_city_name),
                            Toast.LENGTH_LONG
                        ).show()
                        callApi(args.cityName)
                    }
                })
            } else {
                Toast.makeText(context, getString(R.string.no_internet), Toast.LENGTH_SHORT)
                    .show()
                networkViewModel.icon.observe(viewLifecycleOwner, {
                    Log.d(TAG, it.toString())
                    setIcons(it)
                })
            }
        })
    }



    private fun callApi(cityName: String) {
        networkViewModel.callWeatherApi(cityName)
    }

    private fun setListeners() {
        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_weatherFragment_to_downloadFragment)
        }

        refresh_layout.setOnRefreshListener{
            networkViewModel.checkNetworkStatus()
            refresh_layout.isRefreshing = false
        }

        binding.btnSearch.setOnClickListener {
            cityName = et_search.text.toString()
            if (cityName.isNotEmpty()){
                callApi(cityName)
                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(requireView().windowToken, 0)
            }
        }

    }

    private fun setIcons(it: String?) {
        val iconUrl = "${Constants.IMAGE_URL}/$it@2x.png"
        Glide.with(requireActivity()).load(iconUrl).into(iv_icon)

    }

}