package com.testosterolapp.unrd.conversations;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.testosterolapp.unrd.R;
import com.testosterolapp.unrd.data.Chats;
import com.testosterolapp.unrd.data.Data;
import com.testosterolapp.unrd.db.DaoRepository;
import com.testosterolapp.unrd.util.GenericViewHolder;
import com.testosterolapp.unrd.util.QuickContactDivot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FirstScreenAdapter extends RecyclerView.Adapter<GenericViewHolder> {

    private ArrayList<Chats> conversationsList;
    private Context mContext;
    static private Drawable sDefaultContactImage;
    private Chats conversation = null;
    private DaoRepository daoRepository;
    private FirstScreenAdapterChatListener listener;


    public FirstScreenAdapter(Context context, ArrayList<Chats> chats, FirstScreenAdapterChatListener listener) {
        this.mContext = context;
        this.conversationsList = chats;
        this.daoRepository = new DaoRepository(context);
        this.listener = listener;
        if (sDefaultContactImage == null) {
            sDefaultContactImage = context.getResources().getDrawable(R.drawable.ic_contact_picture);
        }
    }


    @Override
    public FirstScreenAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.conversation_layout, parent, false);
        return new MyViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull GenericViewHolder mViewHolder, int i) {
        mViewHolder.onBindView(i);
    }


    @Override
    public int getItemCount() {
        return conversationsList.size();
    }

    @Nullable
    protected Chats getItem(int position) {
        return conversationsList.get(position);
    }

    class MyViewHolder extends GenericViewHolder {
        ConstraintLayout mMainView;
        TextView mSubjectTextView;
        TextView mFromTextView;
        TextView mDateTextView;
        TextView mAmountTextView;
        TextView mDraftTextView;
        QuickContactDivot mAvatarQuickContactDivot;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            mMainView = itemView.findViewById(R.id.mainView);
            mSubjectTextView = itemView.findViewById(R.id.subject);
            mDateTextView = itemView.findViewById(R.id.date);
            mFromTextView = itemView.findViewById(R.id.from);
            mAmountTextView = itemView.findViewById(R.id.amount);
            mDraftTextView = itemView.findViewById(R.id.draft);
            mAvatarQuickContactDivot = itemView.findViewById(R.id.avatar);


            itemView.setOnClickListener(v -> listener.onChatSelected(getItem(getAdapterPosition()), v, getAdapterPosition()));


        }

        @Override
        public void onBindView(int position) {
            if (position <= -1) {
                return;
            }

            try {
                conversation = getItem(position);
            } catch (IndexOutOfBoundsException e) {
                return;
            }
            List<Data> message = null;
            if (conversation != null) {
                message = Objects.requireNonNull(daoRepository.getAllMessagesBasedOnConversationId(conversation.getChat_id()));
            }
            if (message != null && !message.isEmpty()) {
                mAmountTextView.setText(String.valueOf(message.size()));
                mMainView.setBackgroundColor(Color.parseColor("#F2F2F2"));
                mFromTextView.setTypeface(null, Typeface.NORMAL);

            }


            if (message != null && !message.isEmpty()) {
                String name = conversation.getName();
                mFromTextView.setText(name);


                if (daoRepository.getBodyOfLastMessage(conversation.getChat_id()) == null) {
                    mSubjectTextView.setText("");
                } else {
                    mSubjectTextView.setText(daoRepository.getBodyOfLastMessage(conversation.getChat_id()));
                }

                Long charId = getIdOfCharacterOfLastMessageThatIsNotMainCharacter(message);

                daoRepository.getImageOfCharacter(charId, (uri, $completion) -> {
                    Picasso.get().load(uri).into(mAvatarQuickContactDivot);
                    Drawable d = null;
                    if (mAvatarQuickContactDivot != null) {
                        d = mAvatarQuickContactDivot.getDrawable();
                    }
                    if (d != null) {
                        mAvatarQuickContactDivot.setImageDrawable(d);
                        mAvatarQuickContactDivot.assignContactUri(null);
                        mAvatarQuickContactDivot.setVisibility(View.VISIBLE);
                    } else {
                        mAvatarQuickContactDivot.setImageDrawable(sDefaultContactImage);
                        mAvatarQuickContactDivot.assignContactUri(null);
                        mAvatarQuickContactDivot.setVisibility(View.VISIBLE);
                    }
                    return null;
                });


            }
        }
    }

    private Long getIdOfCharacterOfLastMessageThatIsNotMainCharacter(List<Data> message) {
        List<Long> listOfIds = daoRepository.getListOfCharIdsFromData(daoRepository.getMainCharId(), message.get(0).getChat_id());
        return listOfIds.get(listOfIds.size() - 1);
    }

    public interface FirstScreenAdapterChatListener {
        void onChatSelected(Chats chats, View v, int position);
    }

}
