package com.ph30891.lab1_and103_ph30891.ui;

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

import com.ph30891.lab1_and103_ph30891.R;
import com.ph30891.lab1_and103_ph30891.databinding.ActivityLoginAvtivityBinding;
import com.ph30891.lab1_and103_ph30891.presenter.Contract.LoginInterface;
import com.ph30891.lab1_and103_ph30891.presenter.LoginPresenter;

public class LoginAvtivity extends AppCompatActivity implements LoginInterface {
    private ActivityLoginAvtivityBinding binding;
    private  LoginPresenter presenter = new LoginPresenter(this);

    @Override
    protected void onStart() {
        super.onStart();
        // check logged
        presenter.checkLogged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityLoginAvtivityBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.edEmail.getText().toString();
            String password = binding.edPass.getText().toString();
            presenter.isLogin(email, password);
        });
        binding.btnSignUp.setOnClickListener(v -> {
            presenter.gotoSignUp();
        });
        binding.btnLoginWithPhone.setOnClickListener(v -> {
            presenter.gotoLoginPhone();
        });
        binding.btnForgot.setOnClickListener(v -> {
            presenter.resetPassword();
        });

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void loginSuccess() {
        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void loginFailure() {
        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void emailEmpty() {
        binding.tilEmail.setError("Please enter your email address");
    }

    @Override
    public void passwordEmpty() {
        binding.tilPass.setError("Please enter your password");
    }

    @Override
    public void setLoading() {
        binding.btnLogin.setVisibility(View.INVISIBLE);
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopLoading() {
        binding.btnLogin.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void clearErr() {
        binding.tilEmail.setError(null);
        binding.tilPass.setError(null);
    }
}