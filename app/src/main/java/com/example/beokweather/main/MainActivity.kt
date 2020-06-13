package com.example.beokweather.main

import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.beokweather.R
import com.example.beokweather.databinding.ActivityMainBinding
import com.example.beokweather.ext.isNotValidLocationPermission
import com.example.beokweather.util.LocationUtil
import com.example.beokweather.util.Result
import com.example.beokweather.util.isSuccess
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    private val locationManager by lazy {
        getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        if (checkLocationPermission()) return
        showContents()
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        ).apply {
            lifecycleOwner = this@MainActivity
        }
    }

    private fun checkLocationPermission(): Boolean {
        if (isNotValidLocationPermission()) {
            Toast.makeText(
                this,
                R.string.msg_unavailable_app_because_permission_denied,
                Toast.LENGTH_SHORT
            ).show()
            return true
        }
        return false
    }

    private fun showContents() {
        val coordinate = LocationUtil.getCoordinate(locationManager)
        if (coordinate.isSuccess) {
            val data = (coordinate as Result.Success).data
            viewModel.getCurrentWeather(data.lat, data.lon)
        } else {
            val exception = (coordinate as Result.Failure).exception
            Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
        }
    }
}