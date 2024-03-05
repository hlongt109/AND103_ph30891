package com.ph30891.lab1_and103_ph30891.presenter.Contract;


import android.app.Activity;

public interface LoginPhoneInterface {
   Activity getActivity();
   void phoneNumberError();
   void hideErr();
   void setLoadingBtnGet();
   void setLoadingBtnLogin();
   void stopLoadingSet();
   void stopLoadingLogin();
   void getCodeSms(String code);
   void VerificationFailed();
   void VerificationSuccess();
   void otpInvalid();
}
