package com.ph30891.lab6_ph30891.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.ph30891.lab6_ph30891.adapter.ItemImageAdapter;
import com.ph30891.lab6_ph30891.adapter.ItemImageDetailAdapter;
import com.ph30891.lab6_ph30891.databinding.ActivityDetailsBinding;

import com.ph30891.lab6_ph30891.R;
import com.ph30891.lab6_ph30891.model.Fruit;
import com.ph30891.lab6_ph30891.model.Response;
import com.ph30891.lab6_ph30891.networking.HttpRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class DetailsActivity extends AppCompatActivity {
    private ActivityDetailsBinding binding;
    private HttpRequest httpRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String id = getIntent().getStringExtra("id");
        httpRequest = new HttpRequest();
        httpRequest.callApi().getFruitById(id).enqueue(getFruitAPI);

    }
    Callback<Response<Fruit>> getFruitAPI = new Callback<Response<Fruit>>() {
        @Override
        public void onResponse(Call<Response<Fruit>> call, retrofit2.Response<Response<Fruit>> response) {
            if (response.isSuccessful()){
                if (response.body().getStatus() == 200){
                    Fruit fruit = response.body().getData();
                    setDataOnView(fruit);
                }
            }
        }

        @Override
        public void onFailure(Call<Response<Fruit>> call, Throwable t) {

        }
    };
    private void setDataOnView(Fruit fruit){
        setDataImage(fruit.getImages());
        binding.tvName.setText(fruit.getName());
        binding.tvPrice.setText(String.valueOf(fruit.getPrice()));
        binding.tvDes.setText(fruit.getDescription());
    }
    private void setDataImage(ArrayList<String> images){
        ItemImageDetailAdapter adapter = new ItemImageDetailAdapter(this,images);
        binding.rcvImage.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        binding.rcvImage.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}