package com.ph30891.lab6_ph30891.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ph30891.lab6_ph30891.R;
import com.ph30891.lab6_ph30891.databinding.ItemImageBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import kotlin.io.FilesKt;

public class ItemImageAdapter extends RecyclerView.Adapter<ItemImageAdapter.myViewHolder>{
    private Context context;
    private ArrayList<File> listImage;

    public ItemImageAdapter(Context context, ArrayList<File> listImage) {
        this.context = context;
        this.listImage = listImage;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemImageBinding binding = ItemImageBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new myViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        File file = listImage.get(position);
        holder.setData(file);
    }

    @Override
    public int getItemCount() {
        return listImage.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder{
        private ItemImageBinding binding;
        myViewHolder(ItemImageBinding itemImageBinding){
            super(itemImageBinding.getRoot());
            binding = itemImageBinding;
        }
        void setData(File file){
            Glide.with(context).load(file).error(R.drawable.image).into(binding.imageFruits);
        }
    }
}
