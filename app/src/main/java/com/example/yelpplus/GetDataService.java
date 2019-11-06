package com.example.yelpplus;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GetDataService {

    @POST("/loginUser")
    @FormUrlEncoded
    Call<User> getUser(@Field("email_id") String emailId,
                       @Field("password") String password);

    @POST("registerUser")
    @FormUrlEncoded
    Call<User> registerUser(@Field("first_name") String first_name,
                            @Field("last_name") String last_name,
                            @Field("email_id") String emailId,
                            @Field("password") String password);

    @GET("/business")
    Call<List<Business>> getAllBusiness();

    //This call is used to display information on Homepage
    @GET("/category")
    Call<List<Category>> getAllCategory();

    //This call gets list of businesses based on category
    @GET("/business/{id}")
    Call<List<Business>> getBusinessByCategory(@Path("id") String category_id);

    //This call gets list of businesses based on search
    @GET("/search/{word}")
    Call<List<Business>> getBusinessBySearch(@Path("word") String word);

    @GET("/reviews")
    Call<List<Reviews>> getAllReviews();

    //This call gets all the information about a particular business
    @GET("/viewBusiness/{id}")
    Call<List<Business>> getBusinessInformation(@Path("id") String business_id);
}
