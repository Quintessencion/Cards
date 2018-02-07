package com.simbirsoft.igorverbkin.mycards.mvp.rest;

import com.simbirsoft.igorverbkin.mycards.mvp.model.User;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface UserRestService {

    String BASE_URL = "http://api-factory.simbirsoft/";
    String HEADER = "X-Api-Factory-Application-Id:5a65d5222a721a0bc2539958";
    String AUTHORIZATION = "Authorization: Basic NWE2NWQ1MjIyYTcyMWEwYmMyNTM5OTU4OjBlOGNmODdmNjY=";
    String CONTENT_TYPE = "Content-Type:application/json";

    @Headers({HEADER, AUTHORIZATION, CONTENT_TYPE})
    @GET("api/db/Users")
    Flowable<ResponseApiModel<List<User>>> getUser(@Query("login") String login, @Query("password") String password);

    @Headers({HEADER, AUTHORIZATION, CONTENT_TYPE})
    @POST("api/db/Users")
    Flowable<ResponseApiModel<User>> addUser(@Body User user);
}
