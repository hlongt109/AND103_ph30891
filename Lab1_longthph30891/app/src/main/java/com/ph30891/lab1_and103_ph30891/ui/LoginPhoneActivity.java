package com.ph30891.lab1_and103_ph30891.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import com.ph30891.lab1_and103_ph30891.databinding.ActivityLoginPhoneBinding;

import com.ph30891.lab1_and103_ph30891.presenter.Contract.LoginPhoneInterface;
import com.ph30891.lab1_and103_ph30891.presenter.LoginPhonePresenter;

import java.util.concurrent.TimeUnit;

public class LoginPhoneActivity extends AppCompatActivity {
    private ActivityLoginPhoneBinding binding;

    private FirebaseAuth auth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks;
    private String verificatonCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginPhoneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        mcallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                binding.edOPT.setText(phoneAuthCredential.getSmsCode());
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                verificatonCode = s;
            }
        };
        binding.btnGetOtp.setOnClickListener(v -> {
            String phoneNumber = String.valueOf(binding.edPhoneNumber.getText());
            getCodeOTP(phoneNumber);
        });
        binding.btnLogin.setOnClickListener(v -> {
            String code = String.valueOf(binding.edOPT.getText());
            verifyOTP(code);
        });
        binding.btnBack.setOnClickListener(v -> onBackPressed());
    }



    private void getCodeOTP(String phoneNumber) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber("+84" + phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mcallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void verifyOTP(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificatonCode, code);
        signIn(credential);
    }

    private void signIn(PhoneAuthCredential phoneAuthCredential) {

        auth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = task.getResult().getUser();
                        startActivity(new Intent(this, MainActivity.class));
                    } else {
                        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}