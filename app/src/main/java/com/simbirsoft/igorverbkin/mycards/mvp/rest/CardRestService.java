package com.simbirsoft.igorverbkin.mycards.mvp.rest;

import com.simbirsoft.igorverbkin.mycards.mvp.model.Card;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CardRestService {

    String BASE_URL = "http://api-factory.simbirsoft/";
    String HEADER = "X-Api-Factory-Application-Id:5a65d5222a721a0bc2539958";
    String AUTHORIZATION = "Authorization: Basic NWE2NWQ1MjIyYTcyMWEwYmMyNTM5OTU4OjBlOGNmODdmNjY=";
    String CONTENT_TYPE = "Content-Type:application/json";

    @Headers({HEADER, AUTHORIZATION, CONTENT_TYPE})
    @GET("api/db/Map")
    Flowable<ResponseApiModel<List<Card>>> getCardList();

    @Headers({HEADER, AUTHORIZATION, CONTENT_TYPE})
    @PUT("api/db/Map/{id}")
    Flowable<ResponseApiModel<Card>> updateCard(@Body Card card, @Path("id") String id);
}