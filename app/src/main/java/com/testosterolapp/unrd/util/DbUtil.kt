package com.testosterolapp.unrd.util

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.testosterolapp.unrd.R
import com.testosterolapp.unrd.data.*
import com.testosterolapp.unrd.db.DaoRepository
import com.testosterolapp.unrd.db.DaoRepository.OnInsertTimelinesComplete
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject


class DbUtil {

    companion object {

        val TAG: String = DbUtil::class.java.simpleName

        fun launchCoroutineForInsertDataIntoDatabase(dataJSONObject: JSONObject, context: Context) {
            CoroutineScope(Dispatchers.IO).launch {
                insertIntoDatabase(dataJSONObject, context)
            }
        }

        private suspend fun insertIntoDatabase(dataJSONObject: JSONObject, context: Context) {
            if (dataJSONObject.toString() != "{}") { // (tl;dr does the same job as empty check, saves memory)
                insert(dataJSONObject, context)
            } else {
                Log.d(TAG, "data: $dataJSONObject")
            }
        }

        private suspend fun insert(data: JSONObject, context: Context) {
            val daoRepository = DaoRepository(context)

            // status
            val status = Status(data.getJSONObject("status").getLong("code"),
                    data.getJSONObject("status").getString("message"))
            daoRepository.insertStatus(status)

            // result
            val result = data.getJSONObject(context.getString(R.string.result))
            insertResultIntoDatabase(result, daoRepository)
        }

        /**
         * GSON conversion necessary due to the primitive data type exceptions in standard JSON functions
         */
        private suspend fun insertResultIntoDatabase(result: JSONObject, daoRepository: DaoRepository) {
            val resultItemGsonConverted = Gson().fromJson(result.toString(), Result::class.java)
            val resultItem = Result(resultItemGsonConverted.story_id,
                    resultItemGsonConverted.language_id,
                    resultItemGsonConverted.genre_id,
                    resultItemGsonConverted.main_character_id,
                    resultItemGsonConverted.name,
                    resultItemGsonConverted.short_summary,
                    resultItemGsonConverted.full_summary,
                    resultItemGsonConverted.duration,
                    resultItemGsonConverted.price,
                    resultItemGsonConverted.age_from,
                    resultItemGsonConverted.age_to,
                    resultItemGsonConverted.intro_video_sequence,
                    resultItemGsonConverted.story_start_sequence,
                    resultItemGsonConverted.story_end_sequence,
                    resultItemGsonConverted.passcode_clue,
                    resultItemGsonConverted.passcode_value,
                    resultItemGsonConverted.is_coming_soon,
                    resultItemGsonConverted.is_in_testing,
                    resultItemGsonConverted.is_early_access,
                    resultItemGsonConverted.is_published,
                    resultItemGsonConverted.is_featured,
                    resultItemGsonConverted.release_date,
                    resultItemGsonConverted.release_timezone,
                    resultItemGsonConverted.created,
                    resultItemGsonConverted.updated,
                    resultItemGsonConverted.publication_status,
                    resultItemGsonConverted.is_owned,
                    resultItemGsonConverted.purchase_date,
                    resultItemGsonConverted.last_progress_report,
                    resultItemGsonConverted.progress
            )
            val resultId = daoRepository.insertResult(resultItem)
            insertSubArraysIntoDatabase(resultId, result, daoRepository)
        }

        private suspend fun insertSubArraysIntoDatabase(resultId: Long, result: JSONObject, daoRepository: DaoRepository) {
            insertIntroVideoIntoDatabase(result.getJSONArray("intro_video"), daoRepository)
            insertListImagesIntoDatabase(resultId, result.getJSONArray("list_image"), daoRepository)
            insertPreviewMediaIntoDatabase(resultId, result.getJSONArray("preview_media"), daoRepository)
            insertBackgroundImageIntoDatabase(resultId, result.getJSONArray("background_image"), daoRepository)
            insertCharactersIntoDatabase(resultId, result.getJSONArray("characters"), daoRepository)
            insertContactsIntoDatabase(resultId, result.getJSONArray("contacts"), daoRepository)
            insertPurchasedItemsIntoDatabase(resultId, result.getJSONArray("purchased_items"), daoRepository)
            insertTimelineIntoDatabase(resultId, result.getJSONArray("timelines"), daoRepository)
        }


        private suspend fun InsertDataOfEventsOfTimlinesIntoDatabase(eventsIdFk: Long?, data: JSONObject, daoRepository: DaoRepository) {
            daoRepository.insertDataOfEventOfTimeline(getDataForEventItem(data, eventsIdFk))
        }

        private suspend fun insertChatsOfTimelinesIntoDatabase(chats: JSONObject, timelinesIdFk: Long, daoRepository: DaoRepository) {
            val chatsJsonArray = chats.getJSONArray("chats")
            for (j in 0 until chatsJsonArray.length()) {
                val gson = Gson()
                val gsonConverter = gson.fromJson(chatsJsonArray[j].toString(), Chats::class.java)
                val chatItemsToInsert = Chats(timelinesIdFk,
                        gsonConverter.chat_id,
                        gsonConverter.timeline_id,
                        gsonConverter.name,
                        gsonConverter.price,
                        gsonConverter.is_group,
                        gsonConverter.is_locked,
                        gsonConverter.display_name,
                        gsonConverter.owned)
                daoRepository.insertChatsOfTimeline(chatItemsToInsert)
            }
        }

        private suspend fun insertEventsOfTimelinesIntoDatabase(eventsArray: JSONObject, timelinesIdFk: Long,
                                                                daoRepository: DaoRepository) {
            Log.d("kekbr", "YOOOOOOOOOOOOOOOO: ")
            val eventsJsonArray = eventsArray.getJSONArray("events")
            for (j in 0 until eventsJsonArray.length()) {
                val gson = Gson()
                val gsonConverter = gson.fromJson(eventsJsonArray[j].toString(), Events::class.java)
                val eventsToInsert = Events(gsonConverter.type,
                        gsonConverter.sequence,
                        gsonConverter.has_options,
                        timelinesIdFk)
                daoRepository.insertEventsOfTimeline(eventsToInsert, object : DaoRepository.OnInsertEventsComplete {
                    override suspend fun onComplete(events: Long) {
                        Log.d("kekbr", "type: " + eventsJsonArray.getJSONObject(j).getString("type"))
                        if(eventsJsonArray.getJSONObject(j).getString("type").equals("character_shares")){
                            InsertDataSharesOfEventsOfTimlinesIntoDatabase(events, eventsJsonArray.getJSONObject(j).getJSONObject("data"), daoRepository)
                        }else{
                            InsertDataOfEventsOfTimlinesIntoDatabase(events, eventsJsonArray.getJSONObject(j).getJSONObject("data"), daoRepository)
                        }
                    }
                })
            }
        }

        private suspend fun InsertDataSharesOfEventsOfTimlinesIntoDatabase(eventsIdFk: Long?, dataSharesJSONObject: JSONObject, daoRepository: DaoRepository) {
            val gson = Gson()
            val gsonConverter = gson.fromJson(dataSharesJSONObject.toString(), DataShares::class.java)
            val dataSharesOfAnEvent = DataShares(
                    eventsIdFk,
                    gsonConverter.character_share_id,
                    gsonConverter.character_id,
                    gsonConverter.media_duration,
                    gsonConverter.stream_path,
                    gsonConverter.sequence,
                    gsonConverter.duration,
                    gsonConverter.price,
                    gsonConverter.is_locked,
                    gsonConverter.is_live,
                    gsonConverter.is_public,
                    gsonConverter.created,
                    gsonConverter.updated,
                    gsonConverter.resource_id)
            daoRepository.insertDataShares(dataSharesOfAnEvent, object : DaoRepository.OnInsertDataSharesComplete {
                override suspend fun onComplete(dataShares: Long) {
                    InsertMediaOfDataSharesIntoDatabase(gsonConverter.character_id, dataShares, dataSharesJSONObject.getJSONArray("media"), daoRepository)
                }
            })
        }

        private suspend fun InsertMediaOfDataSharesIntoDatabase(char_id: Long?, dataShares: Long, jsonArray: JSONArray, daoRepository: DaoRepository) {
            for (j in 0 until jsonArray.length()) {
                val gson = Gson()
                val gsonConverter = gson.fromJson(jsonArray[j].toString(), Media::class.java)
                val media = Media(
                        char_id,
                        gsonConverter.resource_id,
                        gsonConverter.resource_fid,
                        gsonConverter.resource_type,
                        gsonConverter.resource_uri,
                        gsonConverter.resource_preset,
                        gsonConverter.resource_processed,
                        gsonConverter.resource_progress,
                        dataShares)
                daoRepository.insertMedia(media)
            }
        }

        private suspend fun InsertImagesOfCharactersIntoDatabase(image: JSONObject, characterId: Long,
                                                                 daoRepository: DaoRepository, fk: Long) {
            daoRepository.insertImagesOfCharacter(getImagesOfCharacters(image, characterId, fk))
        }

        private suspend fun insertPurchasedItemsIntoDatabase(resultId: Long,
                                                             purchasedItems: JSONArray,
                                                             daoRepository: DaoRepository) {
            for (j in 0 until purchasedItems.length()) {
                val gson = Gson()
                val gsonConverter = gson.fromJson(purchasedItems[j].toString(), PurchasedItems::class.java)
                val purchasedItemsToInsert = PurchasedItems(
                        gsonConverter.exchange_type,
                        gsonConverter.exchange_key,
                        resultId)
                daoRepository.insertPurchasedItems(purchasedItemsToInsert)
            }
        }

        private suspend fun insertContactsIntoDatabase(resultId: Long, contacts: JSONArray,
                                                       daoRepository: DaoRepository) {
            for (j in 0 until contacts.length()) {
                val gson = Gson()
                val gsonConverter = gson.fromJson(contacts[j].toString(), Contacts::class.java)
                val contactsToInsert = Contacts(
                        gsonConverter.contact_id,
                        resultId)
                daoRepository.insertContacts(contactsToInsert)
            }
        }

        private suspend fun insertCharactersIntoDatabase(resultId: Long, charactersJSONArray: JSONArray, daoRepository: DaoRepository) {
            for (j in 0 until charactersJSONArray.length()) {
                val gson = Gson()
                val gsonConverter = gson.fromJson(charactersJSONArray[j].toString(), Characters::class.java)
                val characterToInsert = Characters(resultId,
                        gsonConverter.character_id,
                        gsonConverter.name,
                        gsonConverter.is_main)
                daoRepository.insertCharacters(characterToInsert, object : DaoRepository.OnInsertCharactersComplete {
                    override suspend fun onComplete(characters: Long) {
                        InsertImagesOfCharactersIntoDatabase(charactersJSONArray.getJSONObject(j).getJSONObject("image"), characters, daoRepository, gsonConverter.character_id!!)
                    }
                })
            }
        }


        private suspend fun insertBackgroundImageIntoDatabase(resultId: Long,
                                                              backgroundImage: JSONArray,
                                                              daoRepository: DaoRepository) {
            for (j in 0 until backgroundImage.length()) {
                val gson = Gson()
                val gsonConverter = gson.fromJson(backgroundImage[j].toString(), BackgroundImage::class.java)
                val backgroundImageToInsert = BackgroundImage(
                        gsonConverter.resource_id,
                        gsonConverter.resource_fid,
                        gsonConverter.resource_type,
                        gsonConverter.resource_uri,
                        gsonConverter.resource_preset,
                        gsonConverter.resource_processed,
                        gsonConverter.resource_progress,
                        resultId)
                daoRepository.insertBackgroundImage(backgroundImageToInsert)
            }
        }

        private suspend fun insertIntroVideoIntoDatabase(introVideo: JSONArray,
                                                         daoRepository: DaoRepository) {
            for (j in 0 until introVideo.length()) {
                val gson = Gson()
                val gsonConverter = gson.fromJson(introVideo[j].toString(), IntroVideo::class.java)
                val introVideoItem = IntroVideo(
                        1,
                        gsonConverter.resource_id,
                        gsonConverter.resource_fid,
                        gsonConverter.resource_type,
                        gsonConverter.resource_uri,
                        gsonConverter.resource_preset,
                        gsonConverter.resource_processed,
                        gsonConverter.resource_progress)
                daoRepository.insertIntroVideo(introVideoItem)
            }
        }

        private suspend fun insertPreviewMediaIntoDatabase(resultId: Long,
                                                           previewMedia: JSONArray,
                                                           daoRepository: DaoRepository) {
            for (j in 0 until previewMedia.length()) {
                val gson = Gson()
                val gsonConverter = gson.fromJson(previewMedia[j].toString(), PreviewMedia::class.java)
                val previewMediaToInsert = PreviewMedia(
                        resultId,
                        gsonConverter.resource_id,
                        gsonConverter.resource_fid,
                        gsonConverter.resource_type,
                        gsonConverter.resource_uri,
                        gsonConverter.resource_preset,
                        gsonConverter.resource_processed,
                        gsonConverter.resource_progress)
                daoRepository.insertPreviewMedia(previewMediaToInsert)
            }
        }

        private suspend fun insertListImagesIntoDatabase(resultId: Long,
                                                         listImage: JSONArray,
                                                         daoRepository: DaoRepository) {
            for (j in 0 until listImage.length()) {
                val gson = Gson()
                val gsonConverter = gson.fromJson(listImage[j].toString(), ListImage::class.java)
                val listImages = ListImage(
                        resultId,
                        gsonConverter.resource_id,
                        gsonConverter.resource_fid,
                        gsonConverter.resource_type,
                        gsonConverter.resource_uri,
                        gsonConverter.resource_preset,
                        gsonConverter.resource_processed,
                        gsonConverter.resource_progress)
                daoRepository.insertListImage(listImages)
            }
        }

        private suspend fun insertTimelineIntoDatabase(resultId: Long, timelineJsonArray: JSONArray,
                                                       daoRepository: DaoRepository) {
            for (j in 0 until timelineJsonArray.length()) {
                val gson = Gson()
                val gsonConverter = gson.fromJson(timelineJsonArray[j].toString(), Timelines::class.java)
                val timelinesToInsert = Timelines(
                        resultId,
                        gsonConverter.timeline_id,
                        gsonConverter.name,
                        gsonConverter.is_primary,
                        gsonConverter.is_terminal,
                        gsonConverter.created,
                        gsonConverter.updated
                )
                daoRepository.insertTimelines(timelinesToInsert, object : OnInsertTimelinesComplete {
                    override suspend fun onComplete(timelines: Long) {
                        insertChatsOfTimelinesIntoDatabase(timelineJsonArray[j] as JSONObject, timelines, daoRepository)
                        insertEventsOfTimelinesIntoDatabase(timelineJsonArray[j] as JSONObject, timelines, daoRepository)
                    }
                })
            }
        }

        private fun getDataForEventItem(data: JSONObject, eventsIdFk: Long?): Data {
            val gson = Gson()
            val gsonConverter = gson.fromJson(data.toString(), Data::class.java)
            val dataOfAnEvent = Data(
                    eventsIdFk,
                    gsonConverter.chat_message_id,
                    gsonConverter.chat_id,
                    gsonConverter.character_id,
                    gsonConverter.media_duration,
                    gsonConverter.content,
                    gsonConverter.url_label,
                    gsonConverter.sequence,
                    gsonConverter.price,
                    gsonConverter.is_locked,
                    gsonConverter.has_options,
                    gsonConverter.options_timeout,
                    gsonConverter.created,
                    gsonConverter.updated,
                    gsonConverter.has_url,
                    gsonConverter.resource_id,
                    gsonConverter.thumb_resource_id,
                    gsonConverter.owned)
            return dataOfAnEvent
        }

        private fun getImagesOfCharacters(image: JSONObject, characterId: Long, fk: Long): Image {
            val gson = Gson()
            val gsonConverter = gson.fromJson(image.toString(), Image::class.java)
            return Image(characterId,
                    fk,
                    gsonConverter?.resource_id,
                    gsonConverter?.resource_fid,
                    gsonConverter?.resource_type,
                    gsonConverter?.resource_uri,
                    gsonConverter?.resource_preset,
                    gsonConverter?.resource_processed,
                    gsonConverter?.resource_progress)
        }

        fun doesDatabaseExist(context: Context, dbName: String): Boolean {
            val dbFile = context.getDatabasePath(dbName)
            return dbFile.exists()
        }
    }
}