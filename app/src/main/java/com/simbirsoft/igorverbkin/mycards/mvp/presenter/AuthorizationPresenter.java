package com.simbirsoft.igorverbkin.mycards.mvp.presenter;

import android.widget.EditText;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.simbirsoft.igorverbkin.mycards.R;
import com.simbirsoft.igorverbkin.mycards.mvp.rest.UserRepository;
import com.simbirsoft.igorverbkin.mycards.mvp.view.AuthorizationView;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

@InjectViewState
public class AuthorizationPresenter extends MvpPresenter<AuthorizationView> {

    private UserRepository repository;
    private CompositeDisposable compositeDisposable;

    public AuthorizationPresenter() {
        repository = UserRepository.newInstance();
        compositeDisposable = new CompositeDisposable();
    }

    public void setViewsObservables(EditText login, EditText password) {
        compositeDisposable.add(Observable.combineLatest(
                RxTextView.textChangeEvents(login),
                RxTextView.textChangeEvents(password),
                (loginObservable, passwordObservable) ->
                        loginObservable.text().length() > 0 && passwordObservable.text().length() > 0)
                .subscribe(enable -> {
                    if (enable) {
                        getViewState().setButtonAppearance(R.string.finish, R.color.green, enable);
                    } else {
                        getViewState().setButtonAppearance(R.string.fields_not_filled, R.color.darkGrey, enable);
                    }
                }));
    }

    public void logIn(String login, String password) {
        compositeDisposable.clear();
        compositeDisposable.add(repository.getUser(login.trim(), password.trim())
                .doOnSubscribe(subscription -> getViewState().showLoading())
                .doFinally(() -> getViewState().hideLoading())
                .subscribe(user -> {
                    if (user.get(0) != null) {
                        getViewState().enter();
                    }
                }, getViewState()::showError));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
