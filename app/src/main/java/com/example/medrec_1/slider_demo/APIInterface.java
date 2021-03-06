package com.example.medrec_1.slider_demo;

import com.example.medrec_1.slider_demo.model.CreateUserResponse;
import com.example.medrec_1.slider_demo.model.DisLikeVedioResponse;
import com.example.medrec_1.slider_demo.model.LikeVedioResponse;
import com.example.medrec_1.slider_demo.model.ViewerResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

interface APIInterface {

    @POST("/GetVideo")
    Call<CreateUserResponse> doGetListResources();

    /*@POST("/api/users")
    Call<CreateUserResponse> createUser(@Body CreateUserResponse user);
*/
    @FormUrlEncoded
    @POST("/GetVideo")
    Call<List<CreateUserResponse>> doCreateUserWithField(@Field("PageNumber") int PageNumber, @Field("PageSize") int PageSize);

    @FormUrlEncoded
    @POST("/GetVideo")
    Call<List<CreateUserResponse>> doCreateUserWithField(@Field("PageNumber") int PageNumber, @Field("PageSize") int PageSize, @Field("StateId") int StateId);

    @FormUrlEncoded
    @POST("/LikeVideo")
    Call<LikeVedioResponse> doLikeVedio(@Field("VideoSourceId") int VideoSourceId, @Field("UserIp") String UserIp);


    @FormUrlEncoded
    @POST("/DisLikeVideo")
    Call<DisLikeVedioResponse> doDisLikeVedio(@Field("VideoSourceId") int VideoSourceId, @Field("UserIp") String UserIp);


    @FormUrlEncoded
    @POST("/SetViews")
    Call<ViewerResponse> doViewers(@Field("VideoSourceId") int VideoSourceId, @Field("UserIp") String UserIp);
}
