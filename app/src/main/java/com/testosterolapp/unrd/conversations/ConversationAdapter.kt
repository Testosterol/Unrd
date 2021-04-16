package com.testosterolapp.unrd.conversations

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.testosterolapp.unrd.R
import com.testosterolapp.unrd.data.Chats
import com.testosterolapp.unrd.data.Data
import com.testosterolapp.unrd.db.DaoRepository
import com.testosterolapp.unrd.messages.MessagesScreenAdapter
import com.testosterolapp.unrd.util.GenericViewHolder
import com.testosterolapp.unrd.util.QuickContactDivot
import java.util.*

class ConversationAdapter(mContext: Context, private val listener: ConversationAdapterChatListener) : PagedListAdapter<Chats, GenericViewHolder>(DiffUtilCallBack()), Filterable {

    private var chatsList: List<Chats>? = null
    private var filterChatsList: List<Chats> = ArrayList()
    private var chat: Chats? = null
    private val daoRepository: DaoRepository = DaoRepository(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val contactView = LayoutInflater.from(parent.context).inflate(R.layout.conversation_layout,
                parent, false)
        return MyViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        holder.onBindView(position)
    }

    inner class MyViewHolder(itemView: View) : GenericViewHolder(itemView) {
        private var mMainView: ConstraintLayout = itemView.findViewById(R.id.mainView)
        private var mSubjectTextView: TextView = itemView.findViewById(R.id.subject)
        private var mFromTextView: TextView = itemView.findViewById(R.id.from)
        private var mDateTextView: TextView = itemView.findViewById(R.id.date)
        private var mAmountTextView: TextView = itemView.findViewById(R.id.amount)
        private var mDraftTextView: TextView = itemView.findViewById(R.id.draft)
        var mAvatarQuickContactDivot: QuickContactDivot? = itemView.findViewById(R.id.avatar)
        override fun onBindView(position: Int) {
            if (position <= -1) {
                return
            }
            chat = try {
                getItem(position)
            } catch (e: IndexOutOfBoundsException) {
                return
            }
            daoRepository.getAllMessagesCountBasedOnConversationId(chat!!.chat_id!!, object : DaoRepository.OnGetAllMessagesCountBasedOnConversationId {
                override suspend fun onComplete(count: Long?) {
                    mAmountTextView.text = count.toString()
                    mMainView.setBackgroundColor(Color.parseColor("#F2F2F2"))
                    mFromTextView.setTypeface(null, Typeface.NORMAL)
                }
            })

            val name = chat!!.name
            mFromTextView.text = name
            daoRepository.getBodyOfLastMessage(chat!!.chat_id, object : DaoRepository.OnGetBodyOfLastMessage {
                override suspend fun onComplete(body: String?) {
                    if (body != null) {
                        mSubjectTextView.text = body
                    } else {
                        mSubjectTextView.text = ""
                    }
                }
            })

            daoRepository.getDateOfLastMessage(chat!!.chat_id, object : DaoRepository.OnGetDateOfLastMessage {
                override suspend fun onComplete(date: String?) {
                    if (date != null) {
                        mDateTextView.text = date
                    } else {
                        mDateTextView.text = ""
                    }
                }
            })

            val charId = getIdOfCharacterOfLastMessageThatIsNotMainCharacter(chat!!.chat_id!!)
            daoRepository.getImageOfCharacter(charId, object : DaoRepository.OnGetImageOfCharacter {
                override suspend fun onComplete(uri: String?) {

                    Glide.with(itemView)
                            .load(uri)
                            .centerCrop()
                            .placeholder(R.drawable.ic_contact_picture)
                            .into(mAvatarQuickContactDivot!!)

                    var d: Drawable? = null
                    if (mAvatarQuickContactDivot != null) {
                        d = mAvatarQuickContactDivot!!.drawable
                    }
                    if (d != null) {
                        mAvatarQuickContactDivot!!.setImageDrawable(d)
                        mAvatarQuickContactDivot!!.assignContactUri(null)
                        mAvatarQuickContactDivot!!.visibility = View.VISIBLE
                    } else {
                        mAvatarQuickContactDivot!!.setImageDrawable(sDefaultContactImage)
                        mAvatarQuickContactDivot!!.assignContactUri(null)
                        mAvatarQuickContactDivot!!.visibility = View.VISIBLE
                    }
                }
            })
        }
        //}

        init {
            itemView.setOnClickListener { v: View? -> listener.onChatSelected(getItem(adapterPosition), v, adapterPosition) }
        }
    }

    override fun submitList(pagedList: PagedList<Chats>?) {
        super.submitList(pagedList)
        this.chatsList = pagedList
        this.filterChatsList = chatsList!!
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return filterChatsList.size
    }

    override fun getItem(position: Int): Chats {
        return if (filterChatsList === chatsList)
            chatsList!![position] else filterChatsList[position]
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    filterChatsList = chatsList!!
                } else {
                    val filteredList: MutableList<Chats> = ArrayList()
                    for (row in chatsList!!) {
                        if (row.display_name!!.contains(charString.toLowerCase())) {
                            if (!filteredList.contains(row)) filteredList.add(row)
                        }
                    }
                    filterChatsList = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = filterChatsList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                submitList(filterResults.values as PagedList<Chats>)
                notifyDataSetChanged()
            }
        }
    }

    private fun getIdOfCharacterOfLastMessageThatIsNotMainCharacter(chat_id: Long): Long? {
        val listOfIds = daoRepository.getListOfCharIdsFromData(daoRepository.getMainCharId()!!, chat_id)
        return listOfIds!![listOfIds.size - 1]
    }

    companion object {
        private var sDefaultContactImage: Drawable? = null
    }

    init {
        if (sDefaultContactImage == null) {
            sDefaultContactImage = mContext.resources.getDrawable(R.drawable.ic_contact_picture)
        }
    }


    interface ConversationAdapterChatListener {
        fun onChatSelected(chats: Chats?, v: View?, position: Int)
    }

}


class DiffUtilCallBack : DiffUtil.ItemCallback<Chats>() {
    override fun areItemsTheSame(oldItem: Chats, newItem: Chats): Boolean {
        return oldItem.chat_id == newItem.chat_id
    }

    override fun areContentsTheSame(oldItem: Chats, newItem: Chats): Boolean {
        return oldItem.name == newItem.name
    }

}