package com.example.startup.api

import com.example.startup.models.*
import retrofit2.Call
import retrofit2.http.*

interface Api {
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("username") username:String,
        @Field("password") password:String
    ):Call<LoginResponse>

    @FormUrlEncoded
    @POST("users")
    fun signUp(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("phone_number") phone_number: String,
        @Field("pin") pin: String

    ):Call<RegisterResponse>

    @FormUrlEncoded
    @POST("users/searchcontact")
    fun searchContact(
        @Field("phone_number") phone_number:String
    ):Call<ContactResponse>

    @FormUrlEncoded
    @PATCH("transaction")
    fun transaction(
        @Field("phone_number") phone_numbe: String,
        @Field("phone_numberr") phone_numberr: String,
        @Field("cash") cash: Long
    ):Call<TransactionResponse>


    @GET("users/{id}")
    fun getUSer(@Path("id") id: Int):Call<LoginResponse>
}