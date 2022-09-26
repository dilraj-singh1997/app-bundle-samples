package com.google.android.samples.dynamicfeatures.ondemand.utility

import com.google.android.samples.dynamicfeatures.ondemand.FeedItem


object Constants {
    const val ID_TYPE_1 = "1"
    const val ID_TYPE_2 = "2"
    const val ID_TYPE_3 = "3"

    val initialFeedList = listOf<FeedItem>(
        FeedItem.FeedItem_1(id = "1234"),
        FeedItem.FeedItem_2(id = "1235"),
        FeedItem.FeedItem_3(id = "1236")
    )
}