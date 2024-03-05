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

public class LoginPhoneActivity extends AppCompatActivity implements LoginPhoneInterface {
    private ActivityLoginPhoneBinding binding;
    private LoginPhonePresenter presenter;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks;
    private String verificatonCode;
    private PhoneAuthProvider.ForceResendingToken resendingToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginPhoneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new LoginPhonePresenter(this);
        PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks1 = presenter.mcallbacks;

//        mcallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//            @Override
//            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//
//                binding.edOPT.setText(phoneAuthCredential.getSmsCode());
//            }
//
//            @Override
//            public void onVerificationFailed(@NonNull FirebaseException e) {
//            }
//
//            @Override
//            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                super.onCodeSent(s, forceResendingToken);
//                verificatonCode = s;
//                resendingToken = forceResendingToken;
//            }
//        };
        init();
    }

    private void init() {
        binding.btnGetOtp.setOnClickListener(v -> {
            String phoneNumber = String.valueOf(binding.edPhoneNumber.getText());
//            getCodeOTP(phoneNumber);
            presenter.getOTP(phoneNumber);
        });
        binding.btnLogin.setOnClickListener(v -> {
            String code = String.valueOf(binding.edOPT.getText());
//            verifyOTP(code);
            presenter.verifyOTP(code);
        });
        binding.btnBack.setOnClickListener(v -> onBackPressed());
    }

//    private void getCodeOTP(String phoneNumber) {
//
//        PhoneAuthOptions options =
//                PhoneAuthOptions.newBuilder(auth)
//                        .setPhoneNumber("+84 " + phoneNumber)
//                        .setTimeout(60L, TimeUnit.SECONDS)
//                        .setActivity(this)
//                        .setCallbacks(mcallbacks)
//                        .build();
//        PhoneAuthProvider.verifyPhoneNumber(options);
//    }

//    private void verifyOTP(String code) {
//        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificatonCode, code);
//        signIn(credential);
//    }
//
//    private void signIn(PhoneAuthCredential phoneAuthCredential) {
//
//        auth.signInWithCredential(phoneAuthCredential)
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
//                        FirebaseUser user = task.getResult().getUser();
//                        startActivity(new Intent(this, MainActivity.class));
//                    } else {
//                        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void phoneNumberError() {
        binding.tilPhoneNumber.setError("Phone Number is invalid");
    }

    @Override
    public void hideErr() {
        binding.tilPhoneNumber.setError(null);
        binding.tilOTP.setError(null);
    }

    @Override
    public void setLoadingBtnGet() {

    }

    @Override
    public void setLoadingBtnLogin() {
       binding.btnLogin.setVisibility(View.INVISIBLE);
       binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopLoadingSet() {

    }

    @Override
    public void stopLoadingLogin() {
        binding.btnLogin.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void getCodeSms(String code) {
        binding.edOPT.setText(code);
    }

    @Override
    public void VerificationFailed() {

    }

    @Override
    public void VerificationSuccess() {

    }

    @Override
    public void otpInvalid() {
        binding.tilOTP.setError("The OTP is invalid");
    }
}