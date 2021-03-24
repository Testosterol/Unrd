package com.testosterolapp.unrd.util;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public abstract class GenericViewHolder extends RecyclerView.ViewHolder {
    public GenericViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void onBindView(int position);
}
