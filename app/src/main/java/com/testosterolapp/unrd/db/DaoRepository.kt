package com.testosterolapp.unrd.db

import android.content.Context
import com.testosterolapp.unrd.data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DaoRepository {

    var resultDao: ResultDao? = null
    var statusDao: StatusDao? = null

    constructor(context: Context) : this() {
        val database = Database.getDatabase(context)
        this.statusDao = database.statusDao()!!
        this.resultDao = database.resultDao()!!
    }

    constructor()

    suspend fun insertResult(result: Result): Long {
        return resultDao?.insert(result)!!
    }

    suspend fun insertListImage(listImage: ListImage): Long {
        return resultDao?.insert(listImage)!!
    }

    suspend fun insertPreviewMedia(previewMedia: PreviewMedia): Long {
        return resultDao?.insert(previewMedia)!!
    }

    suspend fun insertIntroVideo(introVideo: IntroVideo): Long {
        return resultDao?.insert(introVideo)!!
    }

    suspend fun insertBackgroundImage(backgroundImage: BackgroundImage): Long {
        return resultDao?.insert(backgroundImage)!!
    }


    fun insertCharacters(characters: Characters, onComplete: onInsertCharactersComplete?) {
        CoroutineScope(Dispatchers.IO).launch {
            onComplete?.onComplete(resultDao?.insert(characters)!!)
        }

    }

    interface onInsertCharactersComplete {
        suspend fun onComplete(characters: Long)
    }


    fun insertTimelines(timelines: Timelines, onComplete: onInsertTimelinesComplete?) {
        CoroutineScope(Dispatchers.IO).launch {
            onComplete?.onComplete(resultDao?.insert(timelines)!!)
        }

    }

    interface onInsertTimelinesComplete {
        suspend fun onComplete(timelines: Long)
    }


    suspend fun insertContacts(contacts: Contacts): Long {
        return resultDao?.insert(contacts)!!
    }

    suspend fun insertPurchasedItems(purchasedItems: PurchasedItems): Long {
        return resultDao?.insert(purchasedItems)!!
    }

    suspend fun insertImagesOfCharacter(image: Image): Long {
        return resultDao?.insert(image)!!
    }

    fun insertEventsOfTimeline(events: Events, onComplete: onInsertEventsComplete?) {
        CoroutineScope(Dispatchers.IO).launch {
            onComplete?.onComplete(resultDao?.insert(events)!!)
        }

    }

    interface onInsertEventsComplete {
        suspend fun onComplete(events: Long)
    }

    suspend fun insertDataOfEventOfTimeline(data: Data): Long {
        return resultDao?.insert(data)!!
    }

    suspend fun insertChatsOfTimeline(chats: Chats): Long {
        return resultDao?.insert(chats)!!
    }

    suspend fun insertStatus(status: Status): Long {
        return statusDao?.insert(status)!!
    }

    fun getAllMessagesBasedOnConversationId(conversation_id: Long): List<Data?>? {
        return resultDao?.getAllMessagesBasedOnConversationId(conversation_id)
    }

    fun getBodyOfLastMessage(conversation_id: Long?): String? {
        return resultDao?.getBodyOfLastMessage(conversation_id)
    }

    fun getImageOfCharacter(character_id: Long?, onComplete: onGetImageOfCharacter?) {
        CoroutineScope(Dispatchers.Main).launch {
            onComplete?.onComplete(resultDao?.getImageOfCharacter(character_id))
        }
    }

    interface onGetImageOfCharacter {
        suspend fun onComplete(uri: String?)
    }

    fun getIntroVideo(onComplete: onGetIntroVideoComplete?) {
        CoroutineScope(Dispatchers.IO).launch {
            resultDao?.getIntroVideo()?.let { onComplete?.onComplete(it) }
        }

    }

    fun getMainCharId(): Long? {
        return resultDao?.getMainCharId()
    }

    fun getListOfCharIdsFromData(char_id: Long, chat_id: Long): List<Long?>? {
        return resultDao?.getListOfCharIdsFromData(char_id, chat_id)
    }

    fun getChats(): List<Chats>? {
        return resultDao?.getChats()
    }

    fun getAllDataBasedOnChatId(chat_id: Long): List<Data>? {
        return resultDao?.getAllDataBasedOnChatId(chat_id)
    }

    interface onGetIntroVideoComplete {
        suspend fun onComplete(uri: String)
    }

}