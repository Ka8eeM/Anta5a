package com.example.karim.anta5a.APIs;

import com.example.karim.anta5a.models.CustomerDataResponse;
import com.example.karim.anta5a.models.LoginResponse;
import com.example.karim.anta5a.models.MainServices;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {

    @FormUrlEncoded
    @POST("Apiresigter")
    Call<String> register(
            @Field("Name") String name,
            @Field("Gender") String gender,
            @Field("Email") String email,
            @Field("PhoneNumber") String phone,
            @Field("Password") String password,
            @Field("Address") String address,
            @Field("Image") String image
    );

    @FormUrlEncoded
    @POST("Apilogin")
    Call<LoginResponse> userLogin(
            @Field("PhoneNumber") String number,
            @Field("Password") String password
    );

    @GET("ApigetCustomerData/{Id}")
    Call<CustomerDataResponse> getUserLogged(
            @Path("Id") long id
    );

    @POST("deleteAccount/{Id}")
    Call<String> deleteUser(
            @Path("Id") long id
    );

    @FormUrlEncoded
    @POST("ApiUpdateProfile/{Id}")
    Call<String> updateProfile(
            @Path("Id") long id,
            @Field("Name") String name,
            @Field("Email") String email,
            @Field("PhoneNumber") String phone,
            @Field("Address") String address,
            @Field("Gender") String gender
    );

    @FormUrlEncoded
    @POST("ApigetMainServices")
    Call<List<MainServices>>getMainServices();
}