package com.ph30891.lab1_and103_ph30891.presenter;

import android.text.TextUtils;
import android.util.Patterns;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ph30891.lab1_and103_ph30891.presenter.Contract.SignUpInterface;


public class SignUpPresenter {
    private SignUpInterface signUpInterface;

    public SignUpPresenter(SignUpInterface signUpInterface) {
        this.signUpInterface = signUpInterface;
    }

    public void isSignUp(String email, String password) {
        if (validate(email, password)) {
            signUp(email,password);
        }
    }
    private void signUp(String email, String password) {
        loading(true);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                FirebaseUser user = auth.getCurrentUser();
                signUpInterface.signSuccess();
                loading(false);
            }else {
                signUpInterface.signFailure();
                loading(false);
            }
        });
    }
    private boolean validate(String email, String password) {
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            signUpInterface.emailInvalid();
            return false;
        }else {
            signUpInterface.clearErr();
        }

        if(TextUtils.isEmpty(password)){
            signUpInterface.passwordEmpty();
            return false;
        }else {
            signUpInterface.clearErr();
        }
        return true;
    }
    private void loading(Boolean loading){
        if(loading){
            signUpInterface.setLoading();
        }else {
            signUpInterface.stopLoading();
        }
    }
}
