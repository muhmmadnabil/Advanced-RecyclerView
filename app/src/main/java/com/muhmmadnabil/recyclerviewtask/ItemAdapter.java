package com.muhmmadnabil.recyclerviewtask;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private ArrayList<String> list;

    public ItemAdapter(ArrayList<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View item = layoutInflater.inflate(R.layout.custom_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(item);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        String name = list.get(position);
        holder.tvItem.setText(name);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvItem;
        public CardView cardViewItem;

        public ViewHolder(View view) {
            super(view);
            this.tvItem = (TextView) view.findViewById(R.id.tv_item_name);
            this.cardViewItem = (CardView) view.findViewById(R.id.card_view_item);
        }
    }
}
