package com.testosterolapp.unrd.messages

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.testosterolapp.unrd.R
import com.testosterolapp.unrd.data.Data
import com.testosterolapp.unrd.db.DaoRepository
import com.testosterolapp.unrd.db.DaoRepository.OnGetImageOfCharacter
import com.testosterolapp.unrd.util.QuickContactDivot
import java.util.*

open class MessagesScreenAdapter(mContext: Context, private val messagesList: ArrayList<Data>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val daoRepository: DaoRepository = DaoRepository(mContext)

    override fun getItemViewType(position: Int): Int {
        if (messagesList != null) {
            if (position <= messagesList.size) {
                val message = messagesList[position]
                return if (message.character_id != null && message.character_id != MAIN_CHAR_ID) {
                    2
                } else {
                    TYPE_SENT
                }
            }
        }
        return TYPE_SENT
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        return if (i != TYPE_SENT) {
            //SENT UI
            val itemView = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.message_list_sent_item, viewGroup, false)
            SentViewHolder(itemView)
        } else {
            //RECEIVE UI
            val itemView = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.message_list_receive_item, viewGroup, false)
            ReceiveViewHolder(itemView)
        }
    }

    private fun getItem(position: Int): Data {
        return messagesList!![position]
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position <= -1) {
            return
        }
        val data: Data = try {
            getItem(position)
        } catch (e: IndexOutOfBoundsException) {
            return
        }
        if (holder is SentViewHolder) {
            holder.setupViewHolder(data)
        } else if (holder is ReceiveViewHolder) {
            holder.setupViewHolder(data)
        }
    }

    override fun getItemCount(): Int {
        return messagesList!!.size
    }

    internal inner class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var textView: TextView = itemView.findViewById(R.id.text_view)
        private var mDateView: TextView = itemView.findViewById(R.id.date_view)
        private var imageView: ImageView = itemView.findViewById(R.id.image_view)
        private var mAvatarView: QuickContactDivot? = itemView.findViewById(R.id.avatar)
        private var imageButton: ImageButton = itemView.findViewById(R.id.play_slideshow_button)
        fun setupViewHolder(message: Data) {
            mDateView.text = message.updated
            mAvatarView!!.visibility = View.VISIBLE
            daoRepository.getImageOfCharacter(message.character_id, object : OnGetImageOfCharacter {
                override suspend fun onComplete(uri: String?) {
                    Picasso.get().load(uri).into(mAvatarView)
                    var d: Drawable? = null
                    if (mAvatarView != null) {
                        d = mAvatarView!!.drawable
                    }
                    if (d != null) {
                        mAvatarView!!.setImageDrawable(d)
                        mAvatarView!!.visibility = View.VISIBLE
                    } else {
                        mAvatarView!!.setImageDrawable(sDefaultContactImage)
                        mAvatarView!!.visibility = View.VISIBLE
                    }
                }
            })
            if (message.content == null) {
                textView.visibility = View.GONE
            } else {
                textView.visibility = View.VISIBLE
            }
            imageView.visibility = View.GONE
            imageButton.visibility = View.GONE
            if (message.content != null) {
                textView.text = message.content
            } else {
                textView.text = ""
            }
        }

    }

    internal inner class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var textView: TextView = itemView.findViewById(R.id.text_view)
        private var mDateView: TextView = itemView.findViewById(R.id.date_view)
        private var imageView: ImageView = itemView.findViewById(R.id.image_view)
        private var mAvatarView: QuickContactDivot? = itemView.findViewById(R.id.avatar)
        private var imageButton: ImageButton = itemView.findViewById(R.id.play_slideshow_button)
        fun setupViewHolder(message: Data) {
            mDateView.text = message.updated
            daoRepository.getImageOfCharacter(message.character_id, object : OnGetImageOfCharacter {
                override suspend fun onComplete(uri: String?) {
                    Picasso.get().load(uri).into(mAvatarView)
                    var d: Drawable? = null
                    if (mAvatarView != null) {
                        d = mAvatarView!!.drawable
                    }
                    if (d != null) {
                        mAvatarView!!.setImageDrawable(d)
                        mAvatarView!!.visibility = View.VISIBLE
                    } else {
                        mAvatarView!!.setImageDrawable(sDefaultContactImage)
                        mAvatarView!!.visibility = View.VISIBLE
                    }
                }
            })
            textView.visibility = View.VISIBLE
            imageView.visibility = View.GONE
            imageButton.visibility = View.GONE
            if (message.content != null) {
                textView.text = message.content
            } else {
                textView.text = ""
            }
        }

    }

    companion object {
        private const val TYPE_SENT = 1
        private const val MAIN_CHAR_ID: Long = 218
        private var sDefaultContactImage: Drawable? = null
    }

    init {
        if (sDefaultContactImage == null) {
            sDefaultContactImage = mContext.resources.getDrawable(R.drawable.ic_contact_picture)
        }
    }
}