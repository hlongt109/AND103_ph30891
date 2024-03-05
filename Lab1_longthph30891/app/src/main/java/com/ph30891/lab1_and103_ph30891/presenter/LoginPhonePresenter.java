package com.ph30891.lab1_and103_ph30891.presenter;


import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.ph30891.lab1_and103_ph30891.presenter.Contract.LoginPhoneInterface;
import com.ph30891.lab1_and103_ph30891.ui.MainActivity;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class LoginPhonePresenter {
    private LoginPhoneInterface phoneInterface;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String verificatonCode;
    private PhoneAuthProvider.ForceResendingToken resendingToken;

    public LoginPhonePresenter(LoginPhoneInterface phoneInterface) {
        this.phoneInterface = phoneInterface;
    }

    public void getOTP(String phoneNumber) {
        if(validate(phoneNumber)){
            getCodeOTP(phoneNumber);
        }
    }
    private void getCodeOTP(String phoneNumber) {
        loadingSetOtp(true);
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber("+84 " + phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(phoneInterface.getActivity())
                        .setCallbacks(mcallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    public PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                phoneInterface.getCodeSms(phoneAuthCredential.getSmsCode());
                loadingSetOtp(false);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                phoneInterface.VerificationFailed();
                loadingSetOtp(false);
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificatonCode = s;
                resendingToken = forceResendingToken;
                phoneInterface.VerificationSuccess();
                loadingSetOtp(false);
            }
        };


    public void verifyOTP(String code){
        if(validateOtp(code)){
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificatonCode,code);
            signIn(credential);
        }
    }

    private void signIn(PhoneAuthCredential phoneAuthCredential) {
        loadingLogin(true);
        // login and go to next actyvity
        auth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(task -> {
                   if(task.isSuccessful()){
                       loadingLogin(false);
                       Toast.makeText(phoneInterface.getActivity(), "Login success", Toast.LENGTH_SHORT).show();
                       FirebaseUser user = task.getResult().getUser();
                       phoneInterface.getActivity().startActivity(new Intent(phoneInterface.getActivity(), MainActivity.class));
                   }else {
                       loadingLogin(false);
                       Toast.makeText(phoneInterface.getActivity(), "Login failed", Toast.LENGTH_SHORT).show();
                   }
                });

    }


    private boolean validate(String phoneNumber) {
        String regex = "^0\\d{9}$";
        if (!Pattern.matches(regex, phoneNumber)) {
            phoneInterface.phoneNumberError();
            return false;
        } else {
            phoneInterface.hideErr();
        }
        return true;
    }
    private boolean validateOtp(String otp){
        if(TextUtils.isEmpty(otp)){
            phoneInterface.otpInvalid();
            return false;
        } else if (otp.length() < 6) {
            phoneInterface.otpInvalid();
             return false;
        }
        return true;
    }

    private void loadingSetOtp(boolean isLoading) {
        if (isLoading) {
            phoneInterface.setLoadingBtnGet();
        } else {
            phoneInterface.stopLoadingSet();
        }
    }

    private void loadingLogin(boolean isLoading) {
        if (isLoading) {
            phoneInterface.setLoadingBtnLogin();
        } else {
            phoneInterface.stopLoadingLogin();
        }
    }


}
