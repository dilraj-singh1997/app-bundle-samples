package com.google.android.samples.dynamicfeatures.ondemand

import android.app.PictureInPictureParams
import android.app.PictureInPictureUiState
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Rational
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_DRAGGING
import androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_SETTLING
import com.google.android.play.core.splitcompat.SplitCompat
import com.google.android.samples.dynamicfeatures.BaseSplitActivity
import com.google.android.samples.dynamicfeatures.ondemand.kotlin.databinding.ActivityMainKotlinBinding
import com.google.android.samples.dynamicfeatures.ondemand.sideeffects.MainActivitySideEffect
import com.google.android.samples.dynamicfeatures.ondemand.state.MainActivityState
import org.orbitmvi.orbit.viewmodel.observe

class KotlinSampleActivity : BaseSplitActivity() {

    private lateinit var binding: ActivityMainKotlinBinding
    private lateinit var feedAdapter: FeedAdapter

    private val mainViewModel by viewModels<MainViewModel>()

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainKotlinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFeed()
        initObservers()
        initListeners()
    }

    private fun initListeners() {
        binding.btnAddThreeInteractive.setOnClickListener {
            Log.i(TAG, "initListeners: Adding Three Interactive items")
            mainViewModel.addThreeFeedItems()
        }

        binding.btnRemoveCurrent.setOnClickListener {
            Log.i(TAG, "initListeners: removing 0th item")
            mainViewModel.removeCurrent()
        }
    }

    private fun initObservers() {
        mainViewModel.observe(
            state = ::render,
            sideEffect = ::handleSideEffect,
            lifecycleOwner = this
        )
    }

    private fun render(mainActivityState: MainActivityState) {
        feedAdapter.updateFeed(mainActivityState.feed)
    }

    private fun handleSideEffect(state: MainActivitySideEffect) {

    }

    private fun initFeed() {
        feedAdapter = FeedAdapter(supportFragmentManager, this)
        with(binding.vpFeed) {
            adapter = feedAdapter
            registerOnPageChangeCallback(FeedPageChangeCallBack())
            adapter?.registerAdapterDataObserver(FeedAdapterDataObserver())
        }
    }


    inner class FeedPageChangeCallBack : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrollStateChanged(state: Int) {
            Log.i(TAG, "onPageScrollStateChanged: state : ${getScrollState(state)}")
            super.onPageScrollStateChanged(state)
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            Log.i(
                TAG,
                "onPageScrolled: position : $position positionOffset $positionOffset positionOffsetPixels : $positionOffsetPixels"
            )
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            Log.i(TAG, "onPageSelected: position : $position")
            super.onPageSelected(position)
        }
    }

    inner class FeedAdapterDataObserver : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            super.onChanged()
            Log.i(TAG, "onChanged: ")
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            super.onItemRangeChanged(positionStart, itemCount)
            Log.i(
                TAG,
                "onItemRangeChanged: positionStart : $positionStart , itemCount : $itemCount"
            )
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            super.onItemRangeChanged(positionStart, itemCount, payload)
            Log.i(
                TAG,
                "onItemRangeChanged: positionStart : $positionStart itemCount : $itemCount payload : $payload"
            )
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            Log.i(TAG, "onItemRangeInserted: positionStart : $positionStart itemCount : $itemCount")
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            Log.i(TAG, "onItemRangeRemoved: positionStart : $positionStart itemCount : $itemCount")
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount)
            Log.i(
                TAG,
                "onItemRangeMoved: fromPosition : $fromPosition, toPosition : $toPosition, itemCount : $itemCount"
            )
        }
    }

    private fun getScrollState(state: Int) = when (state) {
        ViewPager2.SCROLL_STATE_IDLE -> "SCROLL_STATE_IDLE"
        SCROLL_STATE_DRAGGING -> "SCROLL_STATE_DRAGGING"
        SCROLL_STATE_SETTLING -> "SCROLL_STATE_SETTLING"
        else -> ""
    }

    override fun onUserLeaveHint() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            enterPictureInPictureMode(
                PictureInPictureParams.Builder()
                    .setAspectRatio(Rational(9, 16))
                    .also {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            it.setAutoEnterEnabled(true)
                        }
                    }
                    .build()
            )
        }
    }

    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        }
    }

    override fun onPictureInPictureUiStateChanged(pipState: PictureInPictureUiState) {
        super.onPictureInPictureUiStateChanged(pipState)
        SplitCompat.installActivity(this)
    }
}