package com.google.android.samples.dynamicfeatures.ondemand

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.android.samples.dynamicfeatures.ondemand.sideeffects.MainActivitySideEffect
import com.google.android.samples.dynamicfeatures.ondemand.state.MainActivityState
import com.google.android.samples.dynamicfeatures.ondemand.utility.Constants
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class MainViewModel : ContainerHost<MainActivityState, MainActivitySideEffect>, ViewModel() {


    override val container =
        container<MainActivityState, MainActivitySideEffect>(MainActivityState(Constants.initialFeedList))

    private val mDownStreamEventChannel = Channel<Int>(Channel.UNLIMITED)
    val downStreamEvent = mDownStreamEventChannel.receiveAsFlow()

    init {
        intent {
            startFlow()
        }
    }

    private suspend fun startFlow() {
        var i = 0
        while (true){
            delay(1000)
            mDownStreamEventChannel.trySend(i++)
        }
    }

    fun addThreeFeedItems() = intent {
        val items = mutableStateListOf<FeedItem>()
        items.add(FeedItem.FeedItem_1(id = "1 - ${System.currentTimeMillis()}"))
        items.add(FeedItem.FeedItem_2(id = "2 - ${System.currentTimeMillis()}"))
        items.add(FeedItem.FeedItem_3(id = "3 - ${System.currentTimeMillis()}"))
        reduce {
            val updatedList = state.feed.toMutableList()
            updatedList.addAll(items)
            state.copy(
                feed = updatedList
            )
        }
    }

    fun removeCurrent() = intent {
        reduce {
            val updatedList = state.feed.toMutableList()
            updatedList.removeAt(0)
            state.copy(
                feed = updatedList
            )
        }
    }
}