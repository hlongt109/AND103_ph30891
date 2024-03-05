package com.ph30891.lab1_and103_ph30891.presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ph30891.lab1_and103_ph30891.R;
import com.ph30891.lab1_and103_ph30891.databinding.LayoutResetPassBinding;
import com.ph30891.lab1_and103_ph30891.presenter.Contract.LoginInterface;
import com.ph30891.lab1_and103_ph30891.ui.LoginPhoneActivity;
import com.ph30891.lab1_and103_ph30891.ui.MainActivity;
import com.ph30891.lab1_and103_ph30891.ui.SignUpActivity;

public class LoginPresenter {
    private LoginInterface loginInterface;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    private CountDownTimer countDownTimer;

    public LoginPresenter(LoginInterface loginInterface) {
        this.loginInterface = loginInterface;
    }

    public void resetPassword(){
        AlertDialog.Builder builder = new AlertDialog.Builder(loginInterface.getContext());
        builder.setTitle("Message");
        builder.setMessage("Do you want reset your password ?");
        builder.setNegativeButton("No",null);
        builder.setPositiveButton("Yes",(dialog, which) -> {
             openDiaLogReset();
        });
        builder.create().show();
    }
    public void checkLogged(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if(user != null){
            new Handler().postDelayed(() -> {
                Toast.makeText(loginInterface.getContext(),"Welcome", Toast.LENGTH_SHORT).show();
                loginInterface.getContext().startActivity(new Intent(loginInterface.getContext(),MainActivity.class));
            },2000);
        }
    }
    public void isLogin(String email, String password) {
        if(validate(email,password)){
            login(email,password);
        }
    }

    private void login(String email, String password) {
        loading(true);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                FirebaseUser user = auth.getCurrentUser();
                loginInterface.loginSuccess();
                loading(false);
            }else {
                loginInterface.loginFailure();
                loading(false);
            }
        }).addOnFailureListener(e -> {
           loginInterface.loginFailure();
            loading(false);
        });
    }

    private boolean validate(String email, String password) {
        if(TextUtils.isEmpty(email)){
            loginInterface.emailEmpty();
            return false;
        }else {
            loginInterface.clearErr();
        }

        if(TextUtils.isEmpty(password)){
            loginInterface.passwordEmpty();
            return false;
        }else {
            loginInterface.clearErr();
        }
        return true;
    }
    private void loading(Boolean loading){
        if(loading){
            loginInterface.setLoading();
        }else {
            loginInterface.stopLoading();
        }
    }
    public void gotoSignUp(){
        loginInterface.getContext().startActivity(new Intent(loginInterface.getContext(), SignUpActivity.class));
    }
    public void gotoLoginPhone(){
        loginInterface.getContext().startActivity(new Intent(loginInterface.getContext(), LoginPhoneActivity.class));
    }
    private void openDiaLogReset(){
        AlertDialog.Builder builder = new AlertDialog.Builder(loginInterface.getContext());
        LayoutInflater layoutInflater = ((Activity)loginInterface.getContext()).getLayoutInflater();
        LayoutResetPassBinding binding = LayoutResetPassBinding.inflate(layoutInflater);
        View view = binding.getRoot();
        builder.setView(view);
        builder.setCancelable(false);
        Dialog dialog = builder.create();
        dialog.show();
        binding.btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
            if(countDownTimer != null){
                countDownTimer.cancel();
            }
        });
        binding.btnResetPass.setOnClickListener(v -> {
            String email = String.valueOf(binding.edEmailReset.getText());

            InputMethodManager imm = (InputMethodManager) loginInterface.getContext().getSystemService(loginInterface.getContext().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(binding.edEmailReset.getWindowToken(),0);

            if(TextUtils.isEmpty(email)){
                binding.tilEmailReset.setError("Please enter the email you register with");
            }else {
                binding.tilEmailReset.setError(null);
                auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(loginInterface.getContext(), "Please check you email to update your password", Toast.LENGTH_LONG).show();
                        startCountDown(binding.btnResetPass);
                    }else {
                        Toast.makeText(loginInterface.getContext(), "Error, please try again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void startCountDown(Button button){
        button.setEnabled(false);
        countDownTimer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                button.setText("Resend code " + "( "+ millisUntilFinished / 1000 +" )");
            }
            @Override
            public void onFinish() {
                button.setEnabled(true);
                button.setText("Reset password");
            }
        }.start();
    }
}
