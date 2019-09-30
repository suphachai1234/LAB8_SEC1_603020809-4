package com.example.lab8mysqlqueryinsert


import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface StudentAPI {
    @GET( "allstd")
    fun retrieveStudent(): Call<List<Student>>

    @FormUrlEncoded
    @POST( "std")
    fun insertStd(
        @Field("std_id") std_id: String,
        @Field("std_name") std_name: String,
        @Field("std_age") std_age: Int): Call<Student>
}