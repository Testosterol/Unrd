package com.testosterolapp.unrd.messages

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.testosterolapp.unrd.R
import com.testosterolapp.unrd.db.DaoRepository
import java.util.*

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.messages_activity)
        // Get the Intent that started this activity and extract the string
        val intent = intent
        val chat_id = intent.getLongExtra("chat_id", 0)
        createViews(this, chat_id)
    }

    protected fun createViews(context: Context?, chat_id: Long?) {
        val daoRepository = DaoRepository(context!!)
        val dataList = daoRepository.getAllDataBasedOnChatId(chat_id!!)
        val arrayListOfChats = ArrayList(dataList)
        val recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView
        val adapter = MessagesScreenAdapter(this, arrayListOfChats)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.scrollToPosition(arrayListOfChats.size - 1)
    }
}