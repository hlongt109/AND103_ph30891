package com.ph30891.lab1_and103_ph30891.presenter.Contract;

import android.content.Context;

public interface SignUpInterface {
    Context getContext();
    void signSuccess();
    void signFailure();
    void emailInvalid();
    void passwordEmpty();
    void setLoading();
    void stopLoading();
    void clearErr();
}
