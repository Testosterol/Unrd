package com.testosterolapp.unrd.conversations;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.testosterolapp.unrd.R;
import com.testosterolapp.unrd.data.Chats;
import com.testosterolapp.unrd.db.DaoRepository;
import com.testosterolapp.unrd.messages.ChatActivity;

import java.util.ArrayList;
import java.util.List;

public class FirstScreenActivity extends AppCompatActivity implements FirstScreenAdapter.FirstScreenAdapterChatListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_screen_activity);
        createViews(this);
    }

    protected void createViews(Context context) {

        DaoRepository daoRepository = new DaoRepository(context);
        List<Chats> chatList = daoRepository.getChats();
        ArrayList<Chats> arrayListOfChats = new ArrayList<>(chatList);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        FirstScreenAdapter adapter = new FirstScreenAdapter(this, arrayListOfChats, this);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    @Override
    public void onChatSelected(Chats chats, View v, int position) {
        Intent intent = new Intent(this, ChatActivity.class);
        Long chatId =  chats.getChat_id();
        intent.putExtra("chat_id", chatId);
        startActivity(intent);
    }
}
