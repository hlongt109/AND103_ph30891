package com.ph30891.lab5_retrofit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ph30891.lab5_retrofit.databinding.ItemDistributorBinding;
import com.ph30891.lab5_retrofit.model.Distributor;
import com.ph30891.lab5_retrofit.presenter.contract.Item_Distributor_handle;

import java.util.ArrayList;

public class DistributorAdapter extends RecyclerView.Adapter<DistributorAdapter.myViewHolder>{
    private Context context;
    private ArrayList<Distributor> list;
    private Item_Distributor_handle handle;

    public DistributorAdapter(Context context, ArrayList<Distributor> list) {
        this.context = context;
        this.list = list;
    }
    public void onClickItem(Item_Distributor_handle itemDistributorHandle){
        this.handle = itemDistributorHandle;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDistributorBinding binding = ItemDistributorBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new myViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Distributor distributor = list.get(position);
        holder.setDataOnItem(distributor,position);

        holder.binding.btnDelete.setOnClickListener(v -> {
            handle.Delete(distributor.getId());
        });
        holder.itemView.setOnClickListener(v -> {
            handle.Update(distributor.getId(),distributor);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        private ItemDistributorBinding binding;
        myViewHolder(ItemDistributorBinding distributorBinding){
            super(distributorBinding.getRoot());
            binding = distributorBinding;
        }
        void setDataOnItem(Distributor distributor, int position){
            binding.tvName.setText(distributor.getName());
            binding.tvStt.setText(String.valueOf(position));
        }
    }
}
