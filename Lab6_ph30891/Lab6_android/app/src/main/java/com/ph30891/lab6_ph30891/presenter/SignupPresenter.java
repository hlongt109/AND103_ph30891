package com.ph30891.lab6_ph30891.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Patterns;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ph30891.lab6_ph30891.R;
import com.ph30891.lab6_ph30891.databinding.ActivitySignupBinding;
import com.ph30891.lab6_ph30891.presenter.contract.SignupInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SignupPresenter {
    private SignupInterface signupInterface;
    private ActivitySignupBinding binding;

    private File file;



    public SignupPresenter(SignupInterface signupInterface,ActivitySignupBinding binding) {
        this.signupInterface = signupInterface;
        this.binding = binding;
    }
    public void signUp(String username, String password, String email, String name){
        if(validateSignup(username, password, email,name)){
            handleSignup(username,password,email,name);
        }
    }
    public void openChooseImage(){
        if(ContextCompat.checkSelfPermission(signupInterface.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            // launch(intent)
        }else {
            ActivityCompat.requestPermissions(signupInterface.getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }

    }
    private void handleSignup(String username, String password, String email, String name){

    }
    private boolean validateSignup(String username, String password, String email, String name){
        if(username.isEmpty() || password.isEmpty() || email.isEmpty() || name.isEmpty()){
            if(TextUtils.isEmpty(username)){
                binding.tilUsername.setError("Enter the username");
                return false;
            }else {
                binding.tilUsername.setError(null);
            }
            if(TextUtils.isEmpty(password)){
                binding.tilPass.setError("Enter the password");
                return false;
            }else {
                binding.tilPass.setError(null);
            }
            if(TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.tilEmail.setError("Email is not a valid email address");
                return false;
            }else {
                binding.tilEmail.setError(null);
            }
            if (TextUtils.isEmpty(name)){
                binding.tilName.setError("Enter your name");
                return false;
            }else {
                binding.tilName.setError(null);
            }
        }else {
            return true;
        }
        return true;
    }


    public void getImage(int resultCode, @Nullable Intent data){
        if(resultCode == Activity.RESULT_OK){
            Uri imagePath = data.getData();
            file = createFileFromUri(imagePath,"avatar");
            Glide.with(signupInterface.getContext()).load(file)
                    .circleCrop()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .error(R.drawable.image)
                    .into(binding.imvAvatar);
        }
    }
    private File createFileFromUri(Uri path, String name){
        File file_ = new File(signupInterface.getActivity().getFilesDir(),name + ".jpg");
        try {
            InputStream in = signupInterface.getActivity().getContentResolver().openInputStream(path);
            OutputStream out = new FileOutputStream(file_);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) >0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
            return file_;
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
