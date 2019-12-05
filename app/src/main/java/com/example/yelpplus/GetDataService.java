package com.example.yelpplus;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    //This call is used to display information on Homepage
    @GET("/api/category")
    Call<List<Category>> getAllCategory();

    //Get list of businesses based on category
    @GET("/api/business/viewByCategory/{id}")
    Call<List<Business>> getBusinessByCategory(@Path("id") String category_id);

    //Get list of businesses based on search
    @GET("/api/business/search/{word}")
    Call<List<Business>> getBusinessBySearch(@Path("word") String word);

    //Get all the information about a particular business
    @GET("/api/business/view/{id}")
    Call<Business> getBusinessInformation(@Path("id") String business_id);

    // Get reviews for a user with his id
    @GET("/api/review/{user_id}")
    Call<List<Reviews_profile>> getProfile(@Path("user_id") String user_id);

    // Add review
    @POST("/api/review/{business_id}/{user_id}")
    @FormUrlEncoded
    Call<Reviews_profile> postNewReview(@Path("business_id") String business_id,
                                        @Path("user_id") String user_id,
                                        @Field("service_rating") String service_rating,
                                        @Field("product_rating") String product_rating,
                                        @Field("ambience_rating") String ambience_rating,
                                        @Field("price_rating") String price_rating,
                                        @Field("title") String title,
                                        @Field("description") String description);

    //Claim Business
    @PUT("/api/business/claimBusiness/{business_id}/{user_id}")
    Call<Business> businessClaim(@Path("business_id") String business_id,
                                 @Path("user_id") String user_id);

    //Register business for event booking
    @PUT("/api/business/enableEventBooking/{business_id}")
    Call<Business> registerBusiness(@Path("business_id") String business_id);

    // Save url to db
    @PUT("/api/business/addImage/{business_id}")
    @FormUrlEncoded
    Call<ImageUrl> uploadImage(@Path("business_id") String business_id,
                               @Field("url") String url);

    //Send event booking information to database
    @POST("/api/event/createEvent/{user_id}/{business_id}")
    @FormUrlEncoded
    Call<EventBooking> postNewEvent(@Path("business_id") String business_id,
                                        @Path("user_id") String user_id,
                                        @Field("date") String date,
                                        @Field("time") String time,
                                        @Field("guest_count") String guestCount,
                                        @Field("menu") String[] menu);

    //Get all events for a particular business
    @GET("/api/event/getAllEvents/{business_id}")
    Call<Business> getEventBookingInfo(@Path("business_id") String business_id);

    @POST("/api/user/addFollowing/{user_id}/{secondary_id}")
    Call<User> followUser(@Path("user_id") String user_id,
                          @Path("secondary_id") String secondary_id);

    @GET("/api/event/getEvents/{user_id}")
    Call<List<EventBooking>> getUserEvents(@Path("user_id") String user_id);

    @GET("api/user/getFollowing/{user_id}")
    Call<User> getFollowers(@Path("user_id") String user_id);
}