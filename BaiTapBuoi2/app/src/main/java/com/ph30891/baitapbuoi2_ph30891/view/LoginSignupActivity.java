package com.ph30891.baitapbuoi2_ph30891.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.ph30891.baitapbuoi2_ph30891.R;
import com.ph30891.baitapbuoi2_ph30891.databinding.ActivityLoginSignupBinding;

public class LoginSignupActivity extends AppCompatActivity {
    private ActivityLoginSignupBinding binding;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginSignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        init();
    }
    public void init(){
        binding.btnLogin.setOnClickListener(v -> {
            String email = String.valueOf(binding.edEmail.getText());
            String password = String.valueOf(binding.edPass.getText());
            if(validate(email, password)){
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    }else {
                        Toast.makeText(this, "Email or password is invalid", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        binding.btnSignUp.setOnClickListener(v -> {
            String email = String.valueOf(binding.edEmail.getText());
            String password = String.valueOf(binding.edPass.getText());
            if(validate(email, password)){
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Sign up failed !", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private boolean validate(String email, String password){
        if(TextUtils.isEmpty(email)){
            binding.tilEmail.setError("Enter your email address");
            return false;
        }else {
            binding.tilEmail.setError(null);
        }
        if(TextUtils.isEmpty(password)){
            binding.tilPass.setError("Enter your password");
            return false;
        }else {
            binding.tilPass.setError(null);
        }
        return true;
    }

}