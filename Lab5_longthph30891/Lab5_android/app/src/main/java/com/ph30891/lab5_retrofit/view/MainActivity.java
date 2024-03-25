package com.ph30891.lab5_retrofit.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ph30891.lab5_retrofit.R;
import com.ph30891.lab5_retrofit.databinding.ActivityMainBinding;
import com.ph30891.lab5_retrofit.model.Distributor;
import com.ph30891.lab5_retrofit.presenter.MainPresenter;
import com.ph30891.lab5_retrofit.presenter.contract.MainInterface;
import com.ph30891.lab5_retrofit.services.HttpRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MainInterface {
    private ActivityMainBinding binding;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        presenter = new MainPresenter(this,binding);
        init();
    }
    private void init(){
        presenter.fetchData();
        binding.edSearch.setOnFocusChangeListener((v, hasFocus) -> {

        });
        binding.btnAdd.setOnClickListener(v -> {
           presenter.openDialodAdd();
        });

        presenter.handleSearch(binding.edSearch);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void addSuccess() {
        Toast.makeText(this, "Add success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addFailure() {
        Toast.makeText(this, "Add failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void removeSuccess() {
        Toast.makeText(this, "Remove success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void removeFailure() {
         Toast.makeText(this, "Remove failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateSuccess() {
        Toast.makeText(this, "Update success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateFailure() {
        Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void loading() {
       binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopLoad() {
        binding.progressBar.setVisibility(View.INVISIBLE);
    }
}