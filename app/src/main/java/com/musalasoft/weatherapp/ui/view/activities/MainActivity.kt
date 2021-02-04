package com.musalasoft.weatherapp.ui.view.activities

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.musalasoft.weatherapp.R
import com.musalasoft.weatherapp.data.model.ErrorType
import com.musalasoft.weatherapp.databinding.ActivityMainBinding
import com.musalasoft.weatherapp.ui.view.dialogs.ProgressBarDialog
import com.musalasoft.weatherapp.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var progressBarDialog: ProgressBarDialog
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.viewModel = viewModel
        progressBarDialog = ProgressBarDialog(this)
        connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        viewModel.setInternetConnectivityStatus(connectivityManager.activeNetwork != null)

        if (!viewModel.isLocationLoaded) {
            viewModel.isLocationLoaded = true
            checkLocationPermissions()
        }
        setOnEditorActionListener()
        setOnRefreshListener()
        configureLiveDataObservers()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    if ((ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) ==
                                PackageManager.PERMISSION_GRANTED)
                    ) {
                        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                            viewModel.searchWeatherByCoordinates(
                                longitude = location?.longitude,
                                latitude = location?.latitude
                            )
                        }
                    }
                } else {
                    viewModel.setErrorDetails("no location", ErrorType.LOCATION)
                }
                return
            }
        }
    }

    /**
     * Checks the status of the network connection and executes the weather search
     *
     * @param view [View]
     */
    @Suppress("UNUSED_PARAMETER")
    fun searchWeatherByCity(view: View) {
        viewModel.setInternetConnectivityStatus(connectivityManager.activeNetwork != null)
        viewModel.searchWeatherByQuery()
    }

    /**
     * Sets up the [Observer]s of the Live Data that will be used in the Activity
     */
    private fun configureLiveDataObservers() {
        setIsSearchInProgressLiveDataObserver()
    }

    /**
     * Sets the [SwipeRefreshLayout.OnRefreshListener] for the [sr_main_refreshLayout]
     */
    private fun setOnRefreshListener() {
        sr_main_refreshLayout.setOnRefreshListener(refreshListener())
    }

    /**
     * Sets the [TextView.OnEditorActionListener] for the [et_main_searchQuery]
     */
    private fun setOnEditorActionListener() {
        et_main_searchQuery.setOnEditorActionListener(setSearchQueryOnEditorActionListener())
    }

    /**
     * Checks for the [Manifest.permission.ACCESS_COARSE_LOCATION] permission. If the permission is
     * granted a weather search is executed based on the current coordinates. If the permission is
     * not granted the permission is requested.
     */
    private fun checkLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                viewModel.searchWeatherByCoordinates(
                    longitude = location?.longitude,
                    latitude = location?.latitude
                )
            }
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    /**
     * Sets up the [Observer] of the [MainViewModel.getIsSearchInProgressLiveData] to show the loading
     * dialog when the search is in progress.
     */
    private fun setIsSearchInProgressLiveDataObserver() {
        viewModel.getIsSearchInProgressLiveData().observe(this, { isSearchInProgress ->
            if (isSearchInProgress) {
                showLoading()
            } else {
                hideLoading()
            }
        })
    }

    /**
     * Calls [MainActivity.refreshData] to refresh the data in the screen.
     *
     * @return [SwipeRefreshLayout.OnRefreshListener]
     */
    private fun refreshListener(): SwipeRefreshLayout.OnRefreshListener =
        SwipeRefreshLayout.OnRefreshListener {
            refreshData()
            sr_main_refreshLayout.isRefreshing = false
        }

    /**
     * When the [EditorInfo.IME_ACTION_DONE] action key is pressed the weather search by the
     * query parameter is executed.
     *
     * @return [TextView.OnEditorActionListener]
     */
    private fun setSearchQueryOnEditorActionListener(): TextView.OnEditorActionListener =
        TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.searchWeatherByQuery()
                true
            } else false
        }

    /**
     * Shows the [ProgressBarDialog]
     *
     * @return void
     */
    private fun showLoading() {
        progressBarDialog.show()
    }

    /**
     * Hides the [ProgressBarDialog]
     *
     * @return void
     */
    private fun hideLoading() {
        progressBarDialog.hide()
    }

    /**
     * Calls the [MainViewModel.refreshData] to refresh the data used in this activity
     *
     * @return void
     */
    private fun refreshData() {
        viewModel.refreshData()
    }
}