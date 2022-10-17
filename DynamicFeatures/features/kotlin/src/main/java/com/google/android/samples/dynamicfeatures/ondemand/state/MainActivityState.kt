package com.google.android.samples.dynamicfeatures.ondemand.state

import androidx.compose.runtime.mutableStateListOf
import com.google.android.samples.dynamicfeatures.ondemand.FeedItem

data class MainActivityState(
    val feed: List<FeedItem> = emptyList()
)