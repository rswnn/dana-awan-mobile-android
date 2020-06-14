package com.example.startup.api

import com.example.startup.models.ContactResponse
import com.example.startup.models.DefaultResponse
import com.example.startup.models.LoginResponse
import com.example.startup.models.TransactionResponse
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