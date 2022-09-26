package com.google.android.samples.dynamicfeatures.ondemand

sealed class FeedItem(val feedItemId: String) {
    data class FeedItem_1(val id: String) : FeedItem(feedItemId = id)
    data class FeedItem_2(val id: String) : FeedItem(feedItemId = id)
    data class FeedItem_3(val id: String) : FeedItem(feedItemId = id)
}
