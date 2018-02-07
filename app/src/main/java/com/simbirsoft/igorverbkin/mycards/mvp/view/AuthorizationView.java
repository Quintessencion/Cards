package com.simbirsoft.igorverbkin.mycards.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;

public interface AuthorizationView extends MvpView, LoadingView {

    void showNotification(int message);

    void showError(Throwable t);

    void enter();

    void setButtonAppearance(int value, int color, Boolean enable);
}
