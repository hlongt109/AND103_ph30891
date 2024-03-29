package com.ph30891.lab8_ph30891.presenter;

import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.ph30891.lab8_ph30891.adapter.FruitAdapter;
import com.ph30891.lab8_ph30891.databinding.ActivityMainBinding;
import com.ph30891.lab8_ph30891.model.Fruit;
import com.ph30891.lab8_ph30891.networking.HttpRequest;
import com.ph30891.lab8_ph30891.presenter.contract.MainInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter {
    private MainInterface mainInterface;
    private ActivityMainBinding binding;
    private FruitAdapter adapter;
    private HttpRequest httpRequest;

    public MainPresenter(MainInterface mainInterface, ActivityMainBinding binding) {
        this.mainInterface = mainInterface;
        this.binding = binding;
    }

    public void fetchData() {
        loading(true);
        httpRequest = new HttpRequest();
        httpRequest.callApi().getListFruit().enqueue(getFruitApi);

    }
    Callback<ArrayList<Fruit>> getFruitApi = new Callback<ArrayList<Fruit>>() {
        @Override
        public void onResponse(Call<ArrayList<Fruit>> call, Response<ArrayList<Fruit>> response) {
            if(response.isSuccessful()){
                ArrayList<Fruit> list = response.body();
                getData(list);
                loading(false);
                binding.imvEmpty.setVisibility(View.INVISIBLE);
            }else {
                loading(false);
                Log.e("fetch data fail", "onResponse: ");
            }
        }

        @Override
        public void onFailure(Call<ArrayList<Fruit>> call, Throwable t) {
            Log.e("Fetch data error", "onFailure: "+t.getMessage());
        }
    };
    private void getData(ArrayList<Fruit> list){
        adapter = new FruitAdapter(mainInterface.getContext(), list);
        binding.rcvProduct.setLayoutManager(new GridLayoutManager(mainInterface.getContext(),2));
        binding.rcvProduct.setAdapter(adapter);
    }
    private void loading(boolean isLoading){
        if(isLoading){
            binding.progressBar.setVisibility(View.VISIBLE);
        }else {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
