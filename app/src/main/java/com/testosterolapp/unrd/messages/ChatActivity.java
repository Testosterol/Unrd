package com.testosterolapp.unrd.messages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.testosterolapp.unrd.R;
import com.testosterolapp.unrd.data.Data;
import com.testosterolapp.unrd.db.DaoRepository;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_activity);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        Long chat_id = intent.getLongExtra("chat_id", 0);
        createViews(this, chat_id);
    }

    protected void createViews(Context context, Long chat_id) {

        DaoRepository daoRepository = new DaoRepository(context);
        List<Data> dataList = daoRepository.getAllDataBasedOnChatId(chat_id);
        ArrayList<Data> arrayListOfChats = new ArrayList<>(dataList);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        Long mainCharId = daoRepository.getMainCharId();
        MessagesScreenAdapter adapter = new MessagesScreenAdapter(this, arrayListOfChats, mainCharId);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.scrollToPosition(arrayListOfChats.size() - 1);

    }

}
