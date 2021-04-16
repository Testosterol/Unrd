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


    fun insertCharacters(characters: Characters, onComplete: OnInsertCharactersComplete?) {
        CoroutineScope(Dispatchers.IO).launch {
            onComplete?.onComplete(resultDao?.insert(characters)!!)
        }

    }

    interface OnInsertCharactersComplete {
        suspend fun onComplete(characters: Long)
    }


    fun insertTimelines(timelines: Timelines, onComplete: OnInsertTimelinesComplete?) {
        CoroutineScope(Dispatchers.IO).launch {
            onComplete?.onComplete(resultDao?.insert(timelines)!!)
        }

    }

    interface OnInsertTimelinesComplete {
        suspend fun onComplete(timelines: Long)
    }

    fun insertDataShares(dataShares: DataShares, onComplete: OnInsertDataSharesComplete?) {
        CoroutineScope(Dispatchers.IO).launch {
            onComplete?.onComplete(resultDao?.insert(dataShares)!!)
        }
    }

    interface OnInsertDataSharesComplete {
        suspend fun onComplete(dataShares: Long)
    }

    suspend fun insertMedia(media: Media): Long {
        return resultDao?.insert(media)!!
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

    fun insertEventsOfTimeline(events: Events, onComplete: OnInsertEventsComplete?) {
        CoroutineScope(Dispatchers.IO).launch {
            onComplete?.onComplete(resultDao?.insert(events)!!)
        }
    }

    interface OnInsertEventsComplete {
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

    fun getAllMessagesBasedOnConversationId(conversation_id: Long): List<Data>? {
        return resultDao?.getAllMessagesBasedOnConversationId(conversation_id)
    }

    fun getAllMessagesCountBasedOnConversationId(conversation_id: Long, onComplete: OnGetAllMessagesCountBasedOnConversationId?) {
        CoroutineScope(Dispatchers.Main).launch {
            onComplete?.onComplete(resultDao?.getAllMessagesCountBasedOnConversationId(conversation_id))
        }
    }

    interface OnGetAllMessagesCountBasedOnConversationId {
        suspend fun onComplete(count: Long?)
    }


    fun getBodyOfLastMessage(conversation_id: Long?, onComplete: OnGetBodyOfLastMessage?) {
        CoroutineScope(Dispatchers.Main).launch {
            onComplete?.onComplete(resultDao?.getBodyOfLastMessage(conversation_id))
        }
    }

    interface OnGetBodyOfLastMessage {
        suspend fun onComplete(body: String?)
    }

    fun getDateOfLastMessage(conversation_id: Long?, onComplete: OnGetDateOfLastMessage?) {
        CoroutineScope(Dispatchers.Main).launch {
            onComplete?.onComplete(resultDao?.getDateOfLastMessage(conversation_id))
        }
    }

    interface OnGetDateOfLastMessage {
        suspend fun onComplete(date: String?)
    }

    fun getImageOfCharacter(character_id: Long?, onComplete: OnGetImageOfCharacter?) {
        CoroutineScope(Dispatchers.Main).launch {
            onComplete?.onComplete(resultDao?.getImageOfCharacter(character_id))
        }
    }

    interface OnGetImageOfCharacter {
        suspend fun onComplete(uri: String?)
    }

    fun getIntroVideo(onComplete: OnGetIntroVideoComplete?) {
        CoroutineScope(Dispatchers.IO).launch {
            resultDao?.getIntroVideo()?.let { onComplete?.onComplete(it) }
        }
    }

    interface OnGetIntroVideoComplete {
        suspend fun onComplete(uri: String)
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


}