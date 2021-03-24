package com.testosterolapp.unrd.messages;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.testosterolapp.unrd.R;
import com.testosterolapp.unrd.data.Data;
import com.testosterolapp.unrd.db.DaoRepository;
import com.testosterolapp.unrd.util.QuickContactDivot;

import java.util.ArrayList;

public class MessagesScreenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Data> messagesList;

    private static int TYPE_SENT = 1;
    private static long MAIN_CHAR_ID = 218;

    private Long mainCharId;

    private Context mContext;
    static private Drawable sDefaultContactImage;
    private DaoRepository daoRepository;

    public MessagesScreenAdapter(Context context, ArrayList<Data> dataList, Long mainCharId) {
        this.mContext = context;
        this.messagesList = dataList;
        this.mainCharId = mainCharId;
        this.daoRepository = new DaoRepository(context);
        if (sDefaultContactImage == null) {
            sDefaultContactImage = context.getResources().getDrawable(R.drawable.ic_contact_picture);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (messagesList != null) {
            if (position <= messagesList.size()) {
                Data message = messagesList.get(position);
                if (message != null) {
                    if (message.getCharacter_id() != null && message.getCharacter_id() != (MAIN_CHAR_ID)) {
                        return 2;
                    } else {
                        return TYPE_SENT;
                    }
                }
                return TYPE_SENT;
            }
        }
        return TYPE_SENT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i != TYPE_SENT) {
            //SENT UI
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.message_list_sent_item, viewGroup, false);
            return new SentViewHolder(itemView);
        } else {
            //RECEIVE UI
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.message_list_receive_item, viewGroup, false);
            return new ReceiveViewHolder(itemView);
        }
    }

    @Nullable
    protected Data getItem(int position) {
        return messagesList.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position <= -1) {
            return;
        }

        Data data;
        try {
            data = getItem(position);
        } catch (IndexOutOfBoundsException e) {
            return;
        }

        if (holder instanceof SentViewHolder) {
            if (data != null) {
                ((SentViewHolder) holder).setupViewHolder(data);
            }
        } else if (holder instanceof ReceiveViewHolder) {
            if (data != null) {
                ((ReceiveViewHolder) holder).setupViewHolder(data);
            }
        }

    }


    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    class SentViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        TextView mDateView;
        ImageView imageView;
        ConstraintLayout mMainView;
        QuickContactDivot mAvatarView;
        ImageButton imageButton;

        SentViewHolder(@NonNull View itemView) {
            super(itemView);
            mMainView = itemView.findViewById(R.id.main);
            mDateView = itemView.findViewById(R.id.date_view);
            textView = itemView.findViewById(R.id.text_view);
            imageView = itemView.findViewById(R.id.image_view);
            imageButton = itemView.findViewById(R.id.play_slideshow_button);
            mAvatarView = itemView.findViewById(R.id.avatar);
        }

        private void setupViewHolder(Data message) {
            mDateView.setText(message.getUpdated());
            mAvatarView.setVisibility(View.VISIBLE);
            daoRepository.getImageOfCharacter(message.getCharacter_id(), (uri, $completion) -> {
                Picasso.get().load(uri).into(mAvatarView);
                Drawable d = null;
                if (mAvatarView != null) {
                    d = mAvatarView.getDrawable();
                }
                if (d != null) {
                    mAvatarView.setImageDrawable(d);
                    mAvatarView.setVisibility(View.VISIBLE);
                } else {
                    mAvatarView.setImageDrawable(sDefaultContactImage);
                    mAvatarView.setVisibility(View.VISIBLE);
                }

                return null;
            });


            if (message.getContent() == null) {
                textView.setVisibility(View.GONE);
            } else {
                textView.setVisibility(View.VISIBLE);
            }

            imageView.setVisibility(View.GONE);
            imageButton.setVisibility(View.GONE);
            if (message.getContent() != null) {
                textView.setText(message.getContent());
            } else {
                textView.setText("");
            }
        }
    }

    class ReceiveViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        TextView mDateView;
        ImageView imageView;
        ConstraintLayout mMainView;
        QuickContactDivot mAvatarView;
        ImageButton imageButton;

        ReceiveViewHolder(@NonNull View itemView) {
            super(itemView);
            mMainView = itemView.findViewById(R.id.main);
            mDateView = itemView.findViewById(R.id.date_view);
            textView = itemView.findViewById(R.id.text_view);
            imageView = itemView.findViewById(R.id.image_view);
            imageButton = itemView.findViewById(R.id.play_slideshow_button);
            mAvatarView = itemView.findViewById(R.id.avatar);
        }

        private void setupViewHolder(Data message) {
            mDateView.setText(message.getUpdated());
            daoRepository.getImageOfCharacter(message.getCharacter_id(), (uri, $completion) -> {
                Picasso.get().load(uri).into(mAvatarView);
                Drawable d = null;
                if (mAvatarView != null) {
                    d = mAvatarView.getDrawable();
                }
                if (d != null) {
                    mAvatarView.setImageDrawable(d);
                    mAvatarView.setVisibility(View.VISIBLE);
                } else {
                    mAvatarView.setImageDrawable(sDefaultContactImage);
                    mAvatarView.setVisibility(View.VISIBLE);
                }

                return null;
            });


            textView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
            imageButton.setVisibility(View.GONE);
            if (message.getContent() != null) {
                textView.setText(message.getContent());
            } else {
                textView.setText("");
            }


        }
    }
}
