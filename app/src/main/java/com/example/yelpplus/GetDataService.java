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

    //This call gets all the information about a particular business
    @GET("/business/show/{id}")
    Call<Business> getBusinessInformation(@Path("id") String business_id);

    @GET("/user/{email_id}")
    Call<User> getProfile(@Path("email_id") String email_id);

    @POST("/review/{business_id}/{email_id}/new")
    @FormUrlEncoded
    Call<Reviews_profile> postNewReview(@Path("business_id") String business_id,
                                        @Path("email_id") String email_id,
                                        @Field("service_rating") String service_rating,
                                        @Field("product_rating") String product_rating,
                                        @Field("ambience_rating") String ambience_rating,
                                        @Field("title") String title,
                                        @Field("description") String description);

}
