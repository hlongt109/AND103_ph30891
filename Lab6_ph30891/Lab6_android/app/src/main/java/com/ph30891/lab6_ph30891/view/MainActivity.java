package com.ph30891.lab6_ph30891.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;

import com.ph30891.lab6_ph30891.R;
import com.ph30891.lab6_ph30891.adapter.FruitAdapter;
import com.ph30891.lab6_ph30891.databinding.ActivityMainBinding;
import com.ph30891.lab6_ph30891.model.Fruit;
import com.ph30891.lab6_ph30891.model.Page;
import com.ph30891.lab6_ph30891.model.Response;
import com.ph30891.lab6_ph30891.networking.HttpRequest;
import com.ph30891.lab6_ph30891.presenter.MainPresenter;
import com.ph30891.lab6_ph30891.presenter.contract.HandleOnClickFruit;
import com.ph30891.lab6_ph30891.presenter.contract.LoginInterface;
import com.ph30891.lab6_ph30891.presenter.contract.MainInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity implements MainInterface {
    private ActivityMainBinding binding;
    private MainPresenter mainPresenter;
    private HttpRequest httpRequest;

    private SharedPreferences sharedPreferences;
    private String token;
    private FruitAdapter adapter;

    private int page = 1;
    private int totalPage = 0;
    private ArrayList<Fruit> ds = new ArrayList<>();
    private String sort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mainPresenter = new MainPresenter(this,binding);
        sharedPreferences = getSharedPreferences("INFO", MainActivity.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        httpRequest = new HttpRequest(token);

        init();
    }
    private void init(){
//        mainPresenter.fetchData();  // lab 6
//        httpRequest.callApi().getPageFruit("Bearer " + token, page).enqueue(getListFruitRespone);

//        Map<String,String> map = getMapFilter(page,"","0","-1");
//        httpRequest.callApi().getPageFruit(map).enqueue(getListFruitRespone);

        binding.btnAdd.setOnClickListener(v -> {
            startActivity(new Intent(this, AddActivity.class));
        });
        binding.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (totalPage == page) return;
                if (binding.progressBar.getVisibility() == View.GONE || binding.progressBar.getVisibility() == View.INVISIBLE) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    page++;
//                    httpRequest.callApi().getPageFruit("Bearer " + token, page).enqueue(getListFruitRespone);
                    FilterFruit();
                }
            }
        });

        binding.btnFilter.setOnClickListener(v -> {
            page = 1;
            ds.clear();
            adapter.notifyDataSetChanged();
            FilterFruit();
        });

        // Bai2 - lab7
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.spinner_price, android.R.layout.simple_spinner_item);
        binding.spnFilter.setAdapter(spinnerAdapter);
        binding.spnFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CharSequence value = (CharSequence) parent.getAdapter().getItem(position);
                if(value.toString().equals("Ascending")){
                    sort = "1";
                } else if (value.toString().equals("Decrease")) {
                    sort = "-1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.spnFilter.setSelection(1);
    }
    @Override
    public Context getContext() {
        return this;
    }

    // Lab 7

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
        adapter = new FruitAdapter(this, ds);
        binding.rcvProduct.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rcvProduct.setAdapter(adapter);
        // handle
        adapter.showHandleClick(new HandleOnClickFruit() {
            @Override
            public void details(String id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }

            @Override
            public void delete(String id) {
                 handleDelete(id);
            }

            @Override
            public void update(Fruit fruit) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                intent.putExtra("fruit",fruit);
                startActivity(intent);
            }
        });
    }
     Callback<Response<Page<ArrayList<Fruit>>>> getListFruitRespone = new Callback<Response<Page<ArrayList<Fruit>>>>() {
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

    //
    @Override
    protected void onResume() {
        super.onResume();
        Map<String,String> map = getMapFilter(page,"","0","-1");
        httpRequest.callApi().getPageFruit(map).enqueue(getListFruitRespone);
    }
    private void FilterFruit(){
        String _name = binding.edSearch.getText().toString().equals("") ? "" : binding.edSearch.getText().toString();
        String _price = binding.edSeachPrice.getText().toString().equals("") ? "0" : binding.edSeachPrice.getText().toString();
        String _sort = sort.equals("") ? "-1" : sort;
        Map<String,String> map = getMapFilter(page,_name,_price,_sort);
        httpRequest.callApi().getPageFruit(map).enqueue(getListFruitRespone);
    }
    private Map<String,String> getMapFilter(int _page,String _name,String _price,String _sort){
        Map<String, String> map = new HashMap<>();
        map.put("page",String.valueOf(_page));
        map.put("name",_name);
        map.put("price",_price);
        map.put("sort",_sort);
        return map;
    }
    private void handleDelete(String id){
        httpRequest.callApi().removeFruitById(id).enqueue(new Callback<Response<Fruit>>() {
            @Override
            public void onResponse(Call<Response<Fruit>> call, retrofit2.Response<Response<Fruit>> response) {
                if (response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Delete successfully", Toast.LENGTH_SHORT).show();
                    removeItemFromList(id);
                }
            }

            @Override
            public void onFailure(Call<Response<Fruit>> call, Throwable t) {

            }
        });
    }
    private void removeItemFromList(String id) {
        for (int i = 0; i < ds.size(); i++) {
            if (ds.get(i).get_id().equals(id)) {
                ds.remove(i);
                break;
            }
        }
        adapter.notifyDataSetChanged();
    }
}