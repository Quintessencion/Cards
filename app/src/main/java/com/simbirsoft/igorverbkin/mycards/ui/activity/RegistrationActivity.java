package com.simbirsoft.igorverbkin.mycards.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;
import com.simbirsoft.igorverbkin.mycards.R;
import com.simbirsoft.igorverbkin.mycards.mvp.model.User;
import com.simbirsoft.igorverbkin.mycards.mvp.presenter.RegistrationPresenter;
import com.simbirsoft.igorverbkin.mycards.mvp.view.RegistrationView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.simbirsoft.igorverbkin.mycards.ui.activity.AuthorizationActivity.USER_LOGIN;
import static com.simbirsoft.igorverbkin.mycards.ui.activity.AuthorizationActivity.USER_PASSWORD;

public class RegistrationActivity extends MvpAppCompatActivity implements RegistrationView {

    @InjectPresenter RegistrationPresenter presenter;

    public static final int REQUEST_STORAGE = 1;
    public static final int REQUEST_CAMERA = 2;

    @BindView(R.id.name) EditText name;
    @BindView(R.id.name_warning) TextView nameWarning;
    @BindView(R.id.email) EditText email;
    @BindView(R.id.email_warning) TextView emailWarning;
    @BindView(R.id.login) EditText login;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.avatar) ImageView avatar;
    @BindView(R.id.finish) Button finish;

    private File photoFile;
    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_from);
        ButterKnife.bind(this);

//        name.setText("n");
//        email.setText("email");
//        login.setText("login");
//        password.setText("pass");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        presenter.setViewsObservables(name, email, login, password);
    }

    @Override
    public void hideNameError() {
        nameWarning.setVisibility(VISIBLE);
    }

    @Override
    public void showNameError() {
        nameWarning.setVisibility(INVISIBLE);
    }

    @Override
    public void showEmailError() {
        emailWarning.setVisibility(VISIBLE);
    }

    @Override
    public void hideEmailError() {
        emailWarning.setVisibility(INVISIBLE);
    }

    @Override
    public void turnButton() {
        setButtonAppearance(R.string.finish, R.color.green, true);
    }

    @Override
    public void disableButton() {
        setButtonAppearance(R.string.fields_not_filled, R.color.darkGrey, false);
    }

    private void setButtonAppearance(int text, int color, boolean enable) {
        finish.setText(getString(text));
        finish.setBackgroundColor(getResources().getColor(color));
        finish.setEnabled(enable);
    }

    @OnClick(R.id.finish)
    public void registration() {
        presenter.registerUser(new User(
                name.getText().toString().trim(),
                email.getText().toString().trim(),
                login.getText().toString().trim(),
                password.getText().toString().trim()),
                photoFile);
    }

    @Override
    public void finishRegistration(String login, String password) {
        Toast.makeText(this, R.string.confirmation_registration, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra(USER_LOGIN, login);
        intent.putExtra(USER_PASSWORD, password);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.avatar, R.id.text_avatar})
    public void loadPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_STORAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_STORAGE:
                if (resultCode != RESULT_OK) {
                    return;
                }
                fileUri = data.getData();
                if (fileUri != null) {
                    Cursor cursor = getContentResolver().query(data.getData(),
                            new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                    if (cursor == null) {
                        return;
                    }
                    cursor.moveToFirst();
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    photoFile = new File(path);
                    cursor.close();

                    avatar.setImageURI(fileUri);
                }
                break;
        }
    }

    @Override
    public void showNotification(int message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void showError(Throwable t) {
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}