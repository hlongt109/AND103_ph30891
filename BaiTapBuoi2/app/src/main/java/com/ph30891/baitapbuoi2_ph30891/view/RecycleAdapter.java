package com.ph30891.baitapbuoi2_ph30891.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ph30891.baitapbuoi2_ph30891.R;
import com.ph30891.baitapbuoi2_ph30891.view.model.Message;

import java.util.ArrayList;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Message> list;

    public RecycleAdapter(Context context, ArrayList<Message> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.email.setText(list.get(position).getEmail());
        holder.message.setText(list.get(position).getMessage());
        holder.date.setText(list.get(position).getDateTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView email, message, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.tvEmail);
            message = itemView.findViewById(R.id.tvMessage);
            date = itemView.findViewById(R.id.tvDate);
        }
    }
}
