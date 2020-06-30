package com.kavya.nasa_photooftheday.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.kavya.nasa_photooftheday.R
import com.kavya.nasa_photooftheday.databinding.ActivityMainBinding
import com.kavya.nasa_photooftheday.util.NetworkCheck
import com.kavya.nasa_photooftheday.util.YoutubeUrlFilter
import com.kavya.nasa_photooftheday.viewmodel.ApodViewModel
import com.kavya.nasa_photooftheday.viewmodel.data.ApodData
import com.kavya.nasa_photooftheday.viewmodel.state.Response
import com.kavya.nasa_photooftheday.viewmodel.state.Status
import java.lang.ref.WeakReference


class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG: String = "MainActivity"
    }

    lateinit var mBinding: ActivityMainBinding
    private val mApodVideoModel: ApodViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initUi()
        fetchPicOfDay()

    }

    private fun initUi() {
        setUpCalendar()
        mBinding.playOrZoom.setOnClickListener() {
            launchDetailsActivity()
        }
    }

    private fun setUpCalendar() {
        mBinding.calendarButton.setOnClickListener() {
            toggleVisibility()
        }

        mBinding.calendar.maxDate = System.currentTimeMillis()

        mBinding.calendar.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            val date = "${String.format("%04d", year)}-" +
                    "${String.format("%02d", monthOfYear + 1)}-" +
                    String.format("%02d", dayOfMonth)

            mBinding.calendar.visibility = View.GONE
            mApodVideoModel.apodLiveData.removeObservers(this)
            fetchPicOfDay(date)
        }
    }

    private fun toggleVisibility() {
        if (mBinding.calendar.visibility == View.VISIBLE) {
            mBinding.calendar.visibility = View.GONE
        } else {
            mBinding.calendar.visibility = View.VISIBLE
        }
    }

    private fun launchDetailsActivity() {
        val intent = Intent(this, DetailScreen::class.java)
        val mediaType = mApodVideoModel.getCachedApod().mediaType
        if (mediaType == "image") {
            intent.putExtra("url", mApodVideoModel.getCachedApod().hdUrl)
        } else {
            intent.putExtra("url", mApodVideoModel.getCachedApod().url)
        }
        intent.putExtra("media-type", mApodVideoModel.getCachedApod().mediaType)
        startActivity(intent)
    }

    private fun fetchPicOfDay(date: String? = null) {
        if (NetworkCheck.isConnected(WeakReference(this))) {
            mApodVideoModel.fetchPicOfDay(date ?: "").observe(this,
                Observer {
                    when (it.state) {
                        Status.SUCCESS -> {
                            Log.d(TAG, "SUCCESS")
                            handleSuccessResponse(it)
                            mApodVideoModel.apodLiveData.removeObservers(this)
                        }

                        Status.LOADING -> {
                            Log.d(TAG, "Loading")
                            if (mBinding.contentGroup.visibility == View.VISIBLE) {
                                Snackbar.make(mBinding.root, "Loading", Snackbar.LENGTH_LONG).show()
                            }
                        }

                        Status.ERROR -> {
                            Log.d(TAG, "ERROR")
                            handleErrorResponse()
                            mApodVideoModel.apodLiveData.removeObservers(this)
                        }
                    }

                })
        } else {
            if (mBinding.contentGroup.visibility != View.VISIBLE) {
                mBinding.error.visibility = View.VISIBLE
                mBinding.loading.visibility = View.GONE
            }
            Snackbar.make(mBinding.root, "Check internet connectivity", Snackbar.LENGTH_LONG)
                .show()
        }

    }


    private fun handleSuccessResponse(response: Response<ApodData>) {
        if (mBinding.contentGroup.visibility != View.VISIBLE) {
            mBinding.contentGroup.visibility = View.VISIBLE
            mBinding.loading.visibility = View.GONE
            mBinding.error.visibility = View.GONE
        }

        mBinding.title.text = response.data?.title ?: "Title Unavailable"
        mBinding.description.text = response.data?.explanation ?: "Title Unavailable"

        val backImageUrl: String?

        if (response.data?.mediaType == "image") {
            backImageUrl = response.data?.url
            mBinding.playOrZoom.setImageResource(R.drawable.ic_zoom_out_map_black_24dp)
        } else {
            val videoId =
                YoutubeUrlFilter.extractVideoIdFromUrl(response.data?.url ?: "")
            backImageUrl = "https://img.youtube.com/vi/${videoId}/0.jpg"
            mBinding.playOrZoom.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        }

        Glide.with(this)
            .load(backImageUrl)
            .thumbnail(
                Glide.with(this).load(R.drawable.waiting)
            )
            .fitCenter()
            .into(mBinding.backgroundImage)

    }

    private fun handleErrorResponse() {
        if (mBinding.contentGroup.visibility != View.VISIBLE) {
            mBinding.loading.visibility = View.GONE
            mBinding.error.visibility = View.VISIBLE
        }
        Snackbar.make(mBinding.root, "Something went wrong", Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mApodVideoModel.apodLiveData.hasObservers())
            mApodVideoModel.apodLiveData.removeObservers(this)

    }


}

