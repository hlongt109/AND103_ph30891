package com.ph30891.lab6_ph30891.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ph30891.lab6_ph30891.R;
import com.ph30891.lab6_ph30891.databinding.ActivitySignupBinding;
import com.ph30891.lab6_ph30891.model.Response;
import com.ph30891.lab6_ph30891.model.User;
import com.ph30891.lab6_ph30891.networking.HttpRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class SignupActivity extends AppCompatActivity {
    private ActivitySignupBinding binding;
    private HttpRequest httpRequest;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        binding.btnSignUp.setOnClickListener(v -> {
            String username = String.valueOf(binding.edUsername.getText());
            String pass = String.valueOf(binding.edPass.getText());
            String email = String.valueOf(binding.edEmail.getText());
            String name = String.valueOf(binding.edName.getText());

            signUp(username, pass, email, name);
        });
        binding.imvAvatar.setOnClickListener(v -> {
            openChooseImage();
        });
    }

    public void openChooseImage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            getImage.launch(intent);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

    }

    public void signUp(String username, String password, String email, String name) {
        if (validateSignup(username, password, email, name)) {
            handleSignup(username, password, email, name);
        }
    }

    private void handleSignup(String username, String password, String email, String name) {
        RequestBody _username = RequestBody.create(MediaType.parse("multipart/form-data"),username);
        RequestBody _password = RequestBody.create(MediaType.parse("multipart/form-data"),password);
        RequestBody _email = RequestBody.create(MediaType.parse("multipart/form-data"),email);
        RequestBody _name = RequestBody.create(MediaType.parse("multipart/form-data"),name);
        MultipartBody.Part mPart;
        if(file != null){
            RequestBody responseFile = RequestBody.create(MediaType.parse("image/*"),file);
            mPart = MultipartBody.Part.createFormData("avatar",file.getName(),responseFile);
        }else {
            mPart = null;
        }
        httpRequest.callApi().register(_username,_password,_email,_name,mPart).enqueue(responseUser);
    }
    Callback<Response<User>> responseUser = new Callback<Response<User>>() {
        @Override
        public void onResponse(Call<Response<User>> call, retrofit2.Response<Response<User>> response) {
            if(response.isSuccessful()){
                if(response.body().getStatus() == 200){
                    Toast.makeText(SignupActivity.this, "Register success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                    finish();
                }else {
                    Toast.makeText(SignupActivity.this, "Register faild", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<User>> call, Throwable t) {
            Log.e("Register faile", "onFailure: "+t.getMessage());
        }
    };

    private boolean validateSignup(String username, String password, String email, String name) {

        if (TextUtils.isEmpty(username)) {
            binding.tilUsername.setError("Enter the username");
            return false;
        } else {
            binding.tilUsername.setError(null);
        }
        if (TextUtils.isEmpty(password)) {
            binding.tilPass.setError("Enter the password");
            return false;
        } else {
            binding.tilPass.setError(null);
        }
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.setError("Email is not a valid email address");
            return false;
        } else {
            binding.tilEmail.setError(null);
        }
        if (TextUtils.isEmpty(name)) {
            binding.tilName.setError("Enter your name");
            return false;
        } else {
            binding.tilName.setError(null);
        }
        return true;
    }

    private File createFileFromUri(Uri path, String name) {
        File file_ = new File(SignupActivity.this.getFilesDir(), name + ".jpg");
        try {
            InputStream in = SignupActivity.this.getContentResolver().openInputStream(path);
            OutputStream out = new FileOutputStream(file_);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
            return file_;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    ActivityResultLauncher getImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if (o.getResultCode() == Activity.RESULT_OK) {
                Intent data = o.getData();
                Uri imagePath = data.getData();
                file = createFileFromUri(imagePath, "avatar");
                Glide.with(SignupActivity.this).load(file)
                        .circleCrop()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .error(R.drawable.image)
                        .into(binding.imvAvatar);
            }
        }
    });
}