package com.simbirsoft.igorverbkin.mycards.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.simbirsoft.igorverbkin.mycards.R;
import com.simbirsoft.igorverbkin.mycards.mvp.presenter.AuthorizationPresenter;
import com.simbirsoft.igorverbkin.mycards.mvp.view.AuthorizationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthorizationActivity extends MvpAppCompatActivity implements AuthorizationView {

    public static final String USER_LOGIN = "user_login";
    public static final String USER_PASSWORD = "user_password";
    public static final int ACTION_REGISTRATION = 1;

    @InjectPresenter AuthorizationPresenter presenter;

    @BindView(R.id.error) TextView error;
    @BindView(R.id.login) EditText login;
    @BindView(R.id.input_password) EditText password;
    @BindView(R.id.registration) TextView registration;
    @BindView(R.id.enter) Button enter;
    @BindView(R.id.progress_indicator) ProgressBar progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization_form);
        ButterKnife.bind(this);
//        login.setText("Dead guard");
//        password.setText("47da2193rk86mj");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        presenter.setViewsObservables(login, password);
    }

    @Override
    public void setButtonAppearance(int value, int color, Boolean enable) {
        enter.setText(getString(value));
        enter.setBackgroundColor(getResources().getColor(color));
        enter.setEnabled(enable);
    }

    @OnClick(R.id.enter)
    public void logIn() {
        error.setVisibility(View.INVISIBLE);
        presenter.logIn(login.getText().toString(), password.getText().toString());
    }

    @Override
    public void enter() {
        startActivity(new Intent(this, CardActivity.class));
    }

    @OnClick(R.id.registration)
    public void registration() {
        startActivityForResult(new Intent(this, RegistrationActivity.class), ACTION_REGISTRATION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == ACTION_REGISTRATION) {
            login.setText(data.getStringExtra(USER_LOGIN));
            password.setText(data.getStringExtra(USER_PASSWORD));
        }
    }

    @Override
    public void showNotification(int message) {

    }

    @Override
    public void showLoading() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(Throwable t) {
        error.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}