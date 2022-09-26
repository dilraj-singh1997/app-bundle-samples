package com.google.android.samples.dynamicfeatures.ondemand

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.samples.dynamicfeatures.ondemand.fragment.FragmentType1
import com.google.android.samples.dynamicfeatures.ondemand.fragment.FragmentType2
import com.google.android.samples.dynamicfeatures.ondemand.fragment.FragmentType3
import com.google.android.samples.dynamicfeatures.ondemand.utility.Constants

class FeedAdapter(
    private val mFragmentManager: FragmentManager,
    private val mLifecycleOwner: LifecycleOwner
) : FragmentStateAdapter(mFragmentManager, mLifecycleOwner.lifecycle) {

    private val mFeedDiffer = AsyncListDiffer(this, FeedDiffer())

    override fun getItemCount() = mFeedDiffer.currentList.size

    override fun createFragment(position: Int): Fragment {
        return when (val feedItem = mFeedDiffer.currentList[position]) {
            is FeedItem.FeedItem_1 -> {
                FragmentType1.getFragment(
                    id = feedItem.id
                ).apply {
                    bundleOf(Constants.ID_TYPE_1 to feedItem.id)
                }
            }
            is FeedItem.FeedItem_2 -> {
                FragmentType2.getFragment(
                    id = feedItem.id
                )
            }
            is FeedItem.FeedItem_3 -> {
                FragmentType3.getFragment(
                    id = feedItem.id
                )
            }
        }
    }

    fun updateFeed(feed: List<FeedItem>) {
        mFeedDiffer.submitList(feed)
    }

    inner class FeedDiffer : DiffUtil.ItemCallback<FeedItem>() {
        override fun areItemsTheSame(oldItem: FeedItem, newItem: FeedItem) =
            oldItem.feedItemId == newItem.feedItemId

        override fun areContentsTheSame(oldItem: FeedItem, newItem: FeedItem) =
            oldItem == newItem
    }

    override fun getItemId(position: Int): Long {
        return mFeedDiffer.currentList[position].feedItemId.hashCode().toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return mFeedDiffer.currentList.any { it.feedItemId.hashCode().toLong() == itemId }
    }
}