package com.example.selectprovincescostarica.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClientProvinces {
    private const val BASE_URL = "https://ubicaciones.paginasweb.cr/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val costaRicaApi: Api by lazy {
        retrofit.create(Api::class.java)
    }
}