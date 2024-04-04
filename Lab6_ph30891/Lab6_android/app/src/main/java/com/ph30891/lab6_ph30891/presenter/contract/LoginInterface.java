package com.ph30891.lab6_ph30891.presenter.contract;

import android.app.Activity;
import android.content.Context;

public interface LoginInterface {
    Activity getActivity();
    Context getContext();
    void showLoading();
    void hideLoading();
    void loginSuccess();
    void loginFailure();
}
