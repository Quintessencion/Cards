package com.simbirsoft.igorverbkin.mycards.mvp.presenter;

import android.widget.EditText;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.simbirsoft.igorverbkin.mycards.R;
import com.simbirsoft.igorverbkin.mycards.mvp.model.User;
import com.simbirsoft.igorverbkin.mycards.mvp.rest.UserRepository;
import com.simbirsoft.igorverbkin.mycards.mvp.view.RegistrationView;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

@InjectViewState
public class RegistrationPresenter extends MvpPresenter<RegistrationView> {

    private boolean nameError;
    private boolean emailError;
    private boolean passwordError;

    private UserRepository repository;
    private CompositeDisposable compositeDisposable;

    public RegistrationPresenter() {
        repository = UserRepository.newInstance();
        compositeDisposable = new CompositeDisposable();
    }

    public void setViewsObservables(EditText name, EditText email, EditText login, EditText password) {
        Observable<String> nameObservable = RxTextView.textChangeEvents(name).map(textView -> textView.text().toString().trim());
        Observable<String> emailObservable = RxTextView.textChangeEvents(email).map(textView -> textView.text().toString().trim());
        Observable<String> loginObservable = RxTextView.textChangeEvents(login).map(textView -> textView.text().toString().trim());
        Observable<String> passwordObservable = RxTextView.textChangeEvents(password).map(textView -> textView.text().toString().trim());

        nameObservable.skip(1).subscribe(textName -> {
            nameError = UtilsValidator.validateName(textName);
            if (!nameError) {
                getViewState().hideNameError();
            } else {
                getViewState().showNameError();
            }
        });

        emailObservable.skip(1).subscribe(textEmail -> {
            emailError = UtilsValidator.validateEmail(textEmail);
            if (emailError) {
                getViewState().hideEmailError();
            } else {
                getViewState().showEmailError();
            }
        });

        passwordObservable.skip(1).subscribe(textPassword -> {
            UtilsValidator.validatePassword(textPassword);
        });

        compositeDisposable.add(Observable.combineLatest(
                nameObservable, emailObservable, loginObservable, passwordObservable,
                (nameObservable2, emailObservable2, loginObservable2, passwordObservable2) ->
                        checkLength(nameObservable2) && checkLength(emailObservable2) &&
                                checkLength(loginObservable2) && checkLength(passwordObservable2))
                .subscribe(enable -> {
                    if (enable) {
                        getViewState().turnButton();
                    } else {
                        getViewState().disableButton();
                    }
                }));
    }

    private boolean checkLength(String text) {
        return text.length() > 0;
    }

    public void registerUser(User user, File photoFile) {
        if (!nameError || !emailError) {
            getViewState().showNotification(R.string.wrong_data);
            return;
        }
        if (photoFile != null) {
            repository.addUser(user)
                    .subscribe(addedUser -> {
                        getViewState().finishRegistration(addedUser.getLogin(), addedUser.getPassword());
                    }, getViewState()::showError);
        } else {
            getViewState().showNotification(R.string.add_photo);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}