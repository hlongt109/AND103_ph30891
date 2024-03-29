package com.ph30891.lab8_ph30891.view;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ph30891.lab8_ph30891.R;
import com.ph30891.lab8_ph30891.databinding.ActivityMainBinding;
import com.ph30891.lab8_ph30891.presenter.MainPresenter;
import com.ph30891.lab8_ph30891.presenter.contract.MainInterface;

public class MainActivity extends AppCompatActivity implements MainInterface {
    private ActivityMainBinding binding;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        presenter = new MainPresenter(this, binding);
        presenter.fetchData();
    }

    @Override
    public Context getContext() {
        return this;
    }
}