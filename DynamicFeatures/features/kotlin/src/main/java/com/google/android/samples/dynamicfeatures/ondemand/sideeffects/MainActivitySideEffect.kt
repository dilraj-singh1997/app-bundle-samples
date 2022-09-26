package com.google.android.samples.dynamicfeatures.ondemand.sideeffects

import com.google.android.samples.dynamicfeatures.ondemand.FeedItem


sealed class MainActivitySideEffect {
    data class UpdateFeed(val feedList: List<FeedItem>) : MainActivitySideEffect()
}