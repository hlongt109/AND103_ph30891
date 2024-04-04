package com.ph30891.lab6_ph30891.presenter;

import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;

import com.ph30891.lab6_ph30891.adapter.FruitAdapter;
import com.ph30891.lab6_ph30891.databinding.ActivityMainBinding;
import com.ph30891.lab6_ph30891.model.Fruit;
import com.ph30891.lab6_ph30891.model.Page;
import com.ph30891.lab6_ph30891.model.Response;
import com.ph30891.lab6_ph30891.networking.HttpRequest;
import com.ph30891.lab6_ph30891.presenter.contract.MainInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class MainPresenter {
    private MainInterface mainInterface;
    private ActivityMainBinding binding;
    private SharedPreferences sharedPreferences;
    private String token;

    public MainPresenter(MainInterface mainInterface, ActivityMainBinding binding) {
        this.mainInterface = mainInterface;
        this.binding = binding;
    }

    private HttpRequest httpRequest;
    private FruitAdapter adapter;

    // Lab7
    private int page = 1;
    private int totalPage = 0;
    private ArrayList<Fruit> ds = new ArrayList<>();
    //

    public void fetchData() {
        showLoad(true);
        sharedPreferences = mainInterface.getContext().getSharedPreferences("INFO", mainInterface.getContext().MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        httpRequest = new HttpRequest(token);
        httpRequest.callApi().getListFruit().enqueue(getFruitApi);

    }

    Callback<Response<ArrayList<Fruit>>> getFruitApi = new Callback<Response<ArrayList<Fruit>>>() {
        @Override
        public void onResponse(Call<Response<ArrayList<Fruit>>> call, retrofit2.Response<Response<ArrayList<Fruit>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    ArrayList<Fruit> list = response.body().getData();
//                    getData(list);
                    showLoad(false);
                }
            }
        }

        @Override
        public void onFailure(Call<Response<ArrayList<Fruit>>> call, Throwable t) {
            showLoad(false);
            Log.e("Fetch data error", "onFailure: " + t.getMessage());
        }
    };

    private void getData(ArrayList<Fruit> list) {
        if(binding.progressBar.getVisibility() == View.VISIBLE){
            new Handler().postDelayed(() -> {
                adapter.notifyItemInserted(ds.size() -1);
                binding.progressBar.setVisibility(View.GONE);
                ds.addAll(list);
                adapter.notifyDataSetChanged();
            },1000);
            return;
        }
        ds.addAll(list);
        adapter = new FruitAdapter(mainInterface.getContext(), ds);
        binding.rcvProduct.setLayoutManager(new GridLayoutManager(mainInterface.getContext(), 2));
        binding.rcvProduct.setAdapter(adapter);
    }

    private void showLoad(boolean isLoading) {
        if (isLoading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    // lab 7
    public void fetchListFruit() {
//        httpRequest = new HttpRequest();
//        httpRequest.callApi().getPageFruit("Bearer " + token, page).enqueue(getListFruitRespone);
    }

    public Callback<Response<Page<ArrayList<Fruit>>>> getListFruitRespone = new Callback<Response<Page<ArrayList<Fruit>>>>() {
        @Override
        public void onResponse(Call<Response<Page<ArrayList<Fruit>>>> call, retrofit2.Response<Response<Page<ArrayList<Fruit>>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    totalPage = response.body().getData().getTotalPage();
                    ArrayList<Fruit> _ds = response.body().getData().getData();
                    getData(_ds);
                }
            }
        }

        @Override
        public void onFailure(Call<Response<Page<ArrayList<Fruit>>>> call, Throwable t) {
            Log.e("Load more fail", "onFailure: " + t.getMessage());
        }
    };

    public void loadNestedScrollView(NestedScrollView nestedScrollView) {
        sharedPreferences = mainInterface.getContext().getSharedPreferences("INFO", mainInterface.getContext().MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (totalPage == page) return;
            if (binding.progressBar.getVisibility() == View.GONE || binding.progressBar.getVisibility() == View.INVISIBLE) {
                binding.progressBar.setVisibility(View.VISIBLE);
                page++;
//                httpRequest.callApi().getPageFruit("Bearer " + token, page).enqueue(getListFruitRespone);
            }
        });
    }
}
