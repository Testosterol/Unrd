package com.testosterolapp.unrd.conversations;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.testosterolapp.unrd.data.Chats;
import com.testosterolapp.unrd.db.Database;
import com.testosterolapp.unrd.db.ResultDao;


/**
 * decided to opt out from ViewModel and went with basic recyclerView
 */
public class ConversationViewModel extends AndroidViewModel {

    public LiveData<PagedList<Chats>> allConversations;
    public ResultDao resultDao;
    public MutableLiveData<String> filterTextAll = new MutableLiveData<>();

    public ConversationViewModel(@NonNull Application application) {
        super(application);
        resultDao = Database.Companion.getDatabase(application).resultDao();
    }

    public void init(ResultDao resultDao) {
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(true)
                        .setPageSize(8)
                        .setPrefetchDistance(10)
                        .build();

        allConversations = Transformations.switchMap(filterTextAll, input -> {
            if (input == null || input.equals("") || input.equals("%%")) {
                return new LivePagedListBuilder<>(
                        resultDao.getInitialConversationsInSortedOrder(), pagedListConfig)
                        .build();
            } else {
                return new LivePagedListBuilder<>(
                        resultDao.getInitialConversationsInSortedOrderFiltered(input), pagedListConfig)
                        .build();
            }
        });
    }

}
