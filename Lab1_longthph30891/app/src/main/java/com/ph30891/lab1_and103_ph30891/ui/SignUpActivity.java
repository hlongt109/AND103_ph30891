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
import com.ph30891.lab1_and103_ph30891.databinding.ActivitySignUpBinding;
import com.ph30891.lab1_and103_ph30891.presenter.Contract.SignUpInterface;
import com.ph30891.lab1_and103_ph30891.presenter.SignUpPresenter;

public class SignUpActivity extends AppCompatActivity implements SignUpInterface {
    private ActivitySignUpBinding binding;
    private SignUpPresenter signUpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        signUpPresenter = new SignUpPresenter(this);

        init();
    }
    private void init(){
        binding.btnSignUp.setOnClickListener(v -> {
            String email = String.valueOf(binding.edEmail.getText());
            String password = String.valueOf(binding.edPass.getText());
            signUpPresenter.isSignUp(email,password);
        });
        binding.btnBack.setOnClickListener(v -> onBackPressed());
        binding.btnBackLogin.setOnClickListener(v -> onBackPressed());
        binding.btnLoginWithPhone.setOnClickListener(v -> startActivity(new Intent(this,LoginPhoneActivity.class)));
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void signSuccess() {
        Toast.makeText(this, "Sign up successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginAvtivity.class));
        finish();
    }

    @Override
    public void signFailure() {
        Toast.makeText(this, "Failed, Try again", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void emailInvalid() {
        binding.tilEmail.setError("Email is invalid");
    }

    @Override
    public void passwordEmpty() {
        binding.tilPass.setError("Enter your password");
    }

    @Override
    public void setLoading() {
      binding.btnSignUp.setVisibility(View.INVISIBLE);
      binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopLoading() {
        binding.btnSignUp.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void clearErr() {
        binding.tilEmail.setError(null);
        binding.tilPass.setError(null);
    }
}