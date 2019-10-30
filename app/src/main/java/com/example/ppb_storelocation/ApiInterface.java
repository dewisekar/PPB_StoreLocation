package com.example.ppb_storelocation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("api/coordinate/05111640000004/store")
    @FormUrlEncoded
    Call<Message> saveLonglat(
            @Field("latitude") String latitude,
            @Field("longitude") String longitude
    );
}
