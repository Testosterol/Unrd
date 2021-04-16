package com.testosterolapp.unrd.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.testosterolapp.unrd.data.*

@Dao
interface ResultDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(result: Result): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(timelines: Timelines): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(listImage: ListImage): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(previewMedia: PreviewMedia): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(introVideo: IntroVideo): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(backgroundImage: BackgroundImage): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(characters: Characters): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contacts: Contacts): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(purchasedItems: PurchasedItems): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(image: Image): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(events: Events): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(chats: Chats): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(data: Data): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(dataShares: DataShares): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(media: Media): Long

    @Query("SELECT * FROM result")
    suspend fun getAll(): Result

    @Query("SELECT COUNT(*) FROM result")
    suspend fun isTableEmpty(): Long

    @Query("SELECT id FROM result")
    fun getResultId(): Int

    @Transaction
    @Query("SELECT * FROM result")
    fun getUsersWithPlaylists(): List<ResultWithListImages>

    @Query("SELECT resource_uri  FROM introvideo WHERE id_fk_result = 1")
    suspend fun getIntroVideo(): String

    @Query("SELECT * FROM Chats ORDER BY chat_id DESC")
    fun getChats(): List<Chats>

    @Query("SELECT * FROM Chats ORDER BY chat_id DESC")
    fun getInitialConversationsInSortedOrder(): LiveData<List<Chats>?>

    @Query("SELECT * FROM Chats ORDER BY chat_id DESC ")
    open fun getInitialConversationsInSortedOrderKek(): DataSource.Factory<Int, Chats>

    @Query("SELECT * FROM Chats WHERE name = :input ORDER BY chat_id DESC")
    open fun getInitialConversationsInSortedOrderFilteredKek(input: String?): DataSource.Factory<Int, Chats>

    @Query("SELECT * FROM Chats WHERE name = :input ORDER BY chat_id DESC")
    fun getInitialConversationsInSortedOrderFiltered(input: String): LiveData<List<Chats>?>

    @Query("SELECT * from data WHERE chat_id = :conversation_id ORDER BY updated DESC")
    fun getAllMessagesBasedOnConversationId(conversation_id: Long?): List<Data>?

    @Query("SELECT COUNT(*) from data WHERE chat_id = :conversation_id")
    fun getAllMessagesCountBasedOnConversationId(conversation_id: Long?): Long?

    @Query("SELECT * from data")
    fun getAllData(): List<Data?>?

    @Query("SELECT character_id from data where character_id != :char_id AND chat_id == :chat_id")
    fun getListOfCharIdsFromData(char_id: Long, chat_id: Long): List<Long?>?

    @Query("SELECT content FROM data WHERE chat_id = :conversation_id ORDER BY updated DESC LIMIT 1")
    fun getBodyOfLastMessage(conversation_id: Long?): String?

    @Query("SELECT updated FROM data WHERE chat_id = :conversation_id ORDER BY updated DESC LIMIT 1")
    fun getDateOfLastMessage(conversation_id: Long?): String?

    @Query("SELECT resource_uri FROM image WHERE char_id = :character_id")
    suspend fun getImageOfCharacter(character_id: Long?): String?

    @Query("SELECT char_id FROM image")
    fun getAllfk(): List<Long>?

    @Query("SELECT main_character_id FROM result")
    fun getMainCharId(): Long?


    @Query("SELECT * FROM data WHERE chat_id = :chat_id ORDER BY updated")
    fun getAllDataBasedOnChatId(chat_id: Long): List<Data>?

    @Query("SELECT * FROM data WHERE chat_id = :chat")
    fun getAllFromMessageCharacter(chat: Long?): List<Data?>?


}