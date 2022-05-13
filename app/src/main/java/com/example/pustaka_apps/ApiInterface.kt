package com.example.pustaka_apps

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {
    //untuk endpoint http://localhost/pustaka/MyAPI.php?function=get_penerbit
    @GET("MyAPI.php?function=get_penerbit")
    fun getPenerbit(): Call<ArrayList<PenerbitModel>>

    //untuk endpoint http://localhost/pustaka/MyAPI.php?function=insert_penerbit
    @FormUrlEncoded
    @POST("MyAPI.php?function=insert_penerbit")
    fun addPenerbit(@Field("nama_penerbit") nama: String,
                    @Field("alamat_penerbit") alamat: String) : Call<ResponseModel>
}