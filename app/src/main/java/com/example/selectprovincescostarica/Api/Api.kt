package com.example.selectprovincescostarica.Api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("provincias.json")
    fun getProvinces(): Call<Map<String, String>>

    @GET("provincia/{province}/cantones.json")
    fun getCantones(@Path("province") province: String): Call<Map<String, String>>

    @GET("provincia/{province}/canton/{canton}/distritos.json")
    fun getDistricts(@Path("province") province: String, @Path("canton") canton: String): Call<Map<String, String>>

}