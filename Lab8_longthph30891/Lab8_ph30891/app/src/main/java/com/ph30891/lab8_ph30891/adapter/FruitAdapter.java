package com.ph30891.lab8_ph30891.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ph30891.lab8_ph30891.R;
import com.ph30891.lab8_ph30891.databinding.ItemFruitBinding;
import com.ph30891.lab8_ph30891.model.Fruit;
import com.ph30891.lab8_ph30891.view.OrderActivity;

import java.util.ArrayList;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.myViewHolder>{
    private Context context;
    private ArrayList<Fruit> list;

    public FruitAdapter(Context context, ArrayList<Fruit> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFruitBinding binding = ItemFruitBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new myViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Fruit fruit = list.get(position);
        holder.setDataOnItem(fruit);

        holder.binding.btnBuy.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("item",list.get(holder.getAdapterPosition()));
            Intent intent = new Intent(context, OrderActivity.class);
            intent.putExtras(bundle);
            (context).startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        private ItemFruitBinding binding;
        myViewHolder(ItemFruitBinding itemFruitBinding){
            super(itemFruitBinding.getRoot());
            binding = itemFruitBinding;
        }
        void setDataOnItem(Fruit fruit){
            Glide.with(context).load(fruit.getImage()).error(R.drawable.ic_photo).into(binding.imvFruit);
            binding.tvNameFruit.setText(fruit.getName());
            binding.tvPrice.setText(String.valueOf(fruit.getPrice()));
        }
    }
}
