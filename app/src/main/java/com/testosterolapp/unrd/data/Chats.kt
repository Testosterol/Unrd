package com.testosterolapp.unrd.data

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(foreignKeys = [ForeignKey(entity = Timelines::class,
        parentColumns = arrayOf("id_timelines"),
        childColumns = arrayOf("id_fk_timeline"),
        onDelete = ForeignKey.CASCADE)])
public class Chats {

    @PrimaryKey(autoGenerate = true)
    var id_chats: Int = 0

    var id_fk_timeline: Long? = 0
    var chat_id: Long? = 0
    var timeline_id: Long? = null
    var name: String? = ""
    var price: Long? = null
    var is_group: Boolean? = null
    var is_locked: Boolean? = null
    var display_name: String? = null
    var owned: Boolean? = null



    constructor(id_fk_timeline: Long?, chat_id: Long?, timeline_id: Long?, name: String?, price: Long?, is_group: Boolean?, is_locked: Boolean?, display_name: String?, owned: Boolean?) {
        this.id_fk_timeline = id_fk_timeline
        this.chat_id = chat_id
        this.timeline_id = timeline_id
        this.name = name
        this.price = price
        this.is_group = is_group
        this.is_locked = is_locked
        this.display_name = display_name
        this.owned = owned
    }

    companion object {

        public var DIFF_CALLBACK: DiffUtil.ItemCallback<Chats> = object : DiffUtil.ItemCallback<Chats>() {
            override fun areItemsTheSame(oldItem: Chats, newItem: Chats): Boolean {
                return oldItem.chat_id == newItem.chat_id
            }

            override fun areContentsTheSame(oldItem: Chats, newItem: Chats): Boolean {
                return equals(oldItem,newItem)
            }
        }

        fun equals(chats: Chats, obj: Any): Boolean {
            if (obj === chats) return true
            val id: Chats = obj as Chats
            return id.chat_id == chats.chat_id
        }
    }


}