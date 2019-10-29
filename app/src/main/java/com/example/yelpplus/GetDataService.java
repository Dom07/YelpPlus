package com.example.yelpplus;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GetDataService {

    @POST("/loginUser")
    @FormUrlEncoded
    Call<User> getUser(@Field("emailId") String emailId,
                       @Field("password") String password);

    @POST("registerUser")
    @FormUrlEncoded
    Call<User> registerUser(@Field("first_name") String first_name,
                            @Field("last_name") String last_name,
                            @Field("emailId") String emailId,
                            @Field("password") String password);

    @GET("/business")
    Call<List<Business>> getAllBusiness();

    @GET("/category")
    Call<List<Category>> getAllCategory();
}
