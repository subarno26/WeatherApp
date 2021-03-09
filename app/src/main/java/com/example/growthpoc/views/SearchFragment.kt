package com.example.growthpoc.views

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.growthpoc.R
import com.example.growthpoc.viewmodels.NetworkViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.*
import javax.inject.Inject


class SearchFragment : DaggerFragment() {
    private val TAG = "SearchFragment"

    @Inject
    lateinit var viewmodel : NetworkViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setListeners()
//        viewmodel.checkNetworkStatus()

    }

    private fun setListeners() {
        tv_use_location.setOnClickListener {
            getCurrentLocation()
        }
    }


    private fun getCurrentLocation() {
        if (context?.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&
            context?.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 101)
        }
        else{
            getCityName()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            101 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(context,getString(R.string.permission_granted),Toast.LENGTH_SHORT).show()
                    getCityName()
                }
                else{
                    Toast.makeText(context,getString(R.string.permission_denied),Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCityName() {
        val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        val latitude = gpsLocation!!.latitude
        val longitude = gpsLocation.longitude

        val geoCoder  = Geocoder(context, Locale.getDefault())
        val addresses = geoCoder.getFromLocation(latitude,longitude,1 )
        val cityName = addresses[0].locality.toString()
        Log.d(TAG, "getCityName: $cityName")


        val action = SearchFragmentDirections.actionSearchFragmentToWeatherFragment(cityName)
        findNavController().navigate(action)

    }
}