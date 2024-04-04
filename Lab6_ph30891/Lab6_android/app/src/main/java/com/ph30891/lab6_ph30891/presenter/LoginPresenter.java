package com.ph30891.lab6_ph30891.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.ph30891.lab6_ph30891.model.Response;
import com.ph30891.lab6_ph30891.model.User;
import com.ph30891.lab6_ph30891.networking.HttpRequest;
import com.ph30891.lab6_ph30891.presenter.contract.LoginInterface;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginPresenter {
    private LoginInterface loginInterface;

    public LoginPresenter(LoginInterface loginInterface) {
        this.loginInterface = loginInterface;
    }
    private HttpRequest httpRequest;

    public void login(String username, String password) {
        if (validate(username, password)) {
            handleLogin(username, password);
        }
    }
    private void handleLogin(String username, String password) {
        loading(true);
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        httpRequest = new HttpRequest();
        httpRequest.callApi().login(user).enqueue(responseLogin);

    }
    Callback<Response<User>> responseLogin = new Callback<Response<User>>() {
        @Override
        public void onResponse(Call<Response<User>> call, retrofit2.Response<Response<User>> response) {
            if(response.isSuccessful()){
                if(response.body().getStatus() == 200){
                     SharedPreferences preferences = loginInterface.getContext().getSharedPreferences("INFO",loginInterface.getActivity().MODE_PRIVATE);
                     SharedPreferences.Editor editor = preferences.edit();
                     editor.putString("token",response.body().getToken());
                     editor.putString("refreshToken",response.body().getRefreshToken());
                     editor.putString("id",response.body().getData().get_id());
                     editor.apply();
                    loginInterface.loginSuccess();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<User>> call, Throwable t) {
            loginInterface.loginFailure();
            Log.e("Login error", "onFailure: "+t.getMessage());
        }
    };
    private boolean validate(String username, String password) {
        if(TextUtils.isEmpty(username)){

            return false;
        }
        if(TextUtils.isEmpty(password)){

            return false;
        }
        return true;
    }
    private void loading(boolean isLoading) {
        if(isLoading){
            loginInterface.showLoading();
        }else {
            loginInterface.hideLoading();
        }
    }
}
