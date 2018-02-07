package com.simbirsoft.igorverbkin.mycards.mvp.rest;

import com.google.gson.annotations.SerializedName;

public class ResponseApiModel<T> {

    @SerializedName("data")
    protected T data;

    public T getData() {
        return data;
    }
}
