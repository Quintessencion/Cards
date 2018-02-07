package com.simbirsoft.igorverbkin.mycards.mvp.view;

import com.arellomobile.mvp.MvpView;

public interface RegistrationView extends MvpView {

    void turnButton();

    void disableButton();

    void showError(Throwable t);
    
    void showNotification(int message);

    void hideNameError();

    void showNameError();

    void showEmailError();

    void hideEmailError();

    void finishRegistration(String login, String password);
}
