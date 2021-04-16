package com.testosterolapp.unrd.conversations

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.testosterolapp.unrd.data.Chats
import com.testosterolapp.unrd.db.ResultDao

class ConversationViewModel(application: Application) : AndroidViewModel(application) {

    var allConversations: LiveData<PagedList<Chats>>? = null
    val filterTextAll = MutableLiveData<String>()

    fun init(resultDao: ResultDao) {
        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPageSize(8)
                .setPrefetchDistance(10)
                .build()

        allConversations = Transformations.switchMap<String, PagedList<Chats>>(filterTextAll) { input: String? ->
            if (input == null || input == "" || input == "%%") {
                return@switchMap LivePagedListBuilder(resultDao.getInitialConversationsInSortedOrderKek(),
                        pagedListConfig)
                        .build()
            } else {
                return@switchMap LivePagedListBuilder(
                        resultDao.getInitialConversationsInSortedOrderFilteredKek(input), pagedListConfig)
                        .build()
            }
        }
    }
}