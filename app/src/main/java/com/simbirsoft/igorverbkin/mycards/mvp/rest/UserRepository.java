package com.simbirsoft.igorverbkin.mycards.mvp.rest;

import android.util.Log;

import com.simbirsoft.igorverbkin.mycards.mvp.model.User;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UserRepository {

    private static final UserRepository INSTANCE = new UserRepository();
    private UserRestService service = RestServiceUserProvider.newInstance().getRestService();

    private UserRepository() {
    }

    public static UserRepository newInstance() {
        return INSTANCE;
    }

    public Flowable<List<User>> getUser(String login, String password) {
        return service.getUser(login, password)
                .map(ResponseApiModel::getData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<User> addUser(User user) {
        return service.addUser(user)
                .map(ResponseApiModel::getData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
