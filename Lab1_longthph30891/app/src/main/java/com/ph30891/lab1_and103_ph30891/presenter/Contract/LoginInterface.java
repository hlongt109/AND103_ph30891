package com.ph30891.lab1_and103_ph30891.presenter.Contract;

import android.content.Context;

public interface LoginInterface {
    Context getContext();
    void loginSuccess();
    void loginFailure();
    void emailEmpty();
    void passwordEmpty();
    void setLoading();
    void stopLoading();
    void clearErr();
}
