package com.ph30891.lab5_retrofit.presenter.contract;

import android.app.Activity;
import android.content.Context;

public interface MainInterface {
    Activity getActivity();
    Context getContext();
    void addSuccess();
    void addFailure();
    void removeSuccess();
    void removeFailure();
    void updateSuccess();
    void updateFailure();
    void loading();
    void stopLoad();
}
