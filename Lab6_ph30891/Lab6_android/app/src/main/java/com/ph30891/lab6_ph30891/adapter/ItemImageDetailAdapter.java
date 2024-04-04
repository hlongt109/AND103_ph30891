package com.ph30891.lab6_ph30891.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ph30891.lab6_ph30891.R;
import com.ph30891.lab6_ph30891.databinding.ItemDetailsBinding;
import com.ph30891.lab6_ph30891.databinding.ItemImageBinding;

import java.io.File;
import java.util.ArrayList;

public class ItemImageDetailAdapter extends RecyclerView.Adapter<ItemImageDetailAdapter.myViewHolder>{
    private Context context;
    private ArrayList<String> list;

    public ItemImageDetailAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDetailsBinding binding = ItemDetailsBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new myViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        String url = list.get(position);
        holder.setData(url);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        private ItemDetailsBinding binding;
        myViewHolder(ItemDetailsBinding itemImageBinding){
            super(itemImageBinding.getRoot());
            binding = itemImageBinding;
        }
        void setData(String url){
            Glide.with(context).load(url).error(R.drawable.image).into(binding.imvDetails);
        }
    }
}
