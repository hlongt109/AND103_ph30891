package com.ph30891.lab6_ph30891.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ph30891.lab6_ph30891.R;
import com.ph30891.lab6_ph30891.databinding.ActivityLoginBinding;
import com.ph30891.lab6_ph30891.presenter.LoginPresenter;
import com.ph30891.lab6_ph30891.presenter.contract.LoginInterface;

public class LoginActivity extends AppCompatActivity  implements LoginInterface {
    private LoginPresenter loginPresenter;
    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loginPresenter = new LoginPresenter(this);
        init();
    }
    private void init() {
        binding.btnSignUp.setOnClickListener(v -> {
            startActivity(new Intent(this, SignupActivity.class));
        });
        binding.btnLogin.setOnClickListener(v -> {
            String username = String.valueOf(binding.edUsername.getText());
            String password = String.valueOf(binding.edPass.getText());

            loginPresenter.login(username,password);
        });
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
    public void showLoading() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.btnLogin.setEnabled(false);
    }

    @Override
    public void hideLoading() {
        binding.progressBar.setVisibility(View.INVISIBLE);
        binding.btnLogin.setEnabled(true);
    }

    @Override
    public void loginSuccess() {
        Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finishAffinity();
    }

    @Override
    public void loginFailure() {
        Toast.makeText(this, "Username or password is invalid", Toast.LENGTH_SHORT).show();
    }
}