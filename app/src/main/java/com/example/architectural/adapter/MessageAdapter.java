package com.example.architectural.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.architectural.R;
import com.example.architectural.model.MessageModel;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private Context context;
    private ArrayList<MessageModel> messageModelArrayList;

    public MessageAdapter(Context context, ArrayList<MessageModel> messageModelArrayList) {
        this.context = context;
        this.messageModelArrayList = messageModelArrayList;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        holder.textView.setText(messageModelArrayList.get(position).getUser());
        holder.textView2.setText(messageModelArrayList.get(position).getMessage());
        holder.textView3.setText(messageModelArrayList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return messageModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        TextView textView2;
        TextView textView3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
        }
    }

    public void addMessage(MessageModel messageModel) {
        messageModelArrayList.add(0, messageModel);
        notifyDataSetChanged();
    }
}
