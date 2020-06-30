package com.kavya.nasa_photooftheday.view

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.kavya.nasa_photooftheday.R
import com.kavya.nasa_photooftheday.databinding.ActivityDetailScreenBinding
import com.kavya.nasa_photooftheday.util.NetworkCheck
import com.kavya.nasa_photooftheday.util.YoutubeUrlFilter
import java.lang.ref.WeakReference


class DetailScreen(var player: YouTubePlayer? = null) : YouTubeBaseActivity(),
    YouTubePlayer.OnInitializedListener {

    private lateinit var mBinding: ActivityDetailScreenBinding
    private var url: String? = null
    private var isVideo: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_screen)

        url = intent.getStringExtra("url")
        isVideo = when (intent.getStringExtra("media-type")) {
            "image" -> false
            else -> true
        }

        if (isVideo) {
            initVideo()
        } else {
            initImage()
        }

    }

    private fun initImage() {
        mBinding.fullscreenImage.visibility = View.VISIBLE
        mBinding.fullscreenVideo.visibility = View.GONE
        Glide
            .with(this)
            .load(url)
            .thumbnail(
                Glide.with(this).load(R.drawable.waiting)
            )
            .error(
                Glide.with(this).load(R.drawable.error)
            )
            .into(mBinding.fullscreenImage)

        if (!NetworkCheck.isConnected(WeakReference(this))) {
            Snackbar.make(mBinding.root, "Check internet connectivity", Snackbar.LENGTH_LONG)
                .show()
        }
    }

    private fun initVideo() {
        mBinding.fullscreenImage.visibility = View.GONE
        mBinding.fullscreenVideo.visibility = View.VISIBLE
        mBinding.fullscreenVideo.initialize("AIzaSyCRKeq_GDMkFL43t2Q39Wc2qz2Ue0R4iIc", this)
    }


    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        player: YouTubePlayer?,
        wasRestored: Boolean
    ) {
        if (this.player == null) {
            this.player = player
        }
        if (!wasRestored)
            url?.let {
                player?.loadVideo(YoutubeUrlFilter.extractVideoIdFromUrl(it));
                player?.play();
                return
            }
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        result: YouTubeInitializationResult?
    ) {
    }

    override fun onDestroy() {
        super.onDestroy()

        if (player != null) {
            player?.release()
        }
        player = null
    }


}
