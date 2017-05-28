package com.example.kota.kotlintraining.network

import com.example.kota.kotlintraining.network.entity.Shot
import com.example.kota.kotlintraining.network.entity.Shots
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface DribbbleRxService {

    @GET("/v1/$PATH_SHOTS?$ACCESS_TOKEN")
    fun getShots(@Query("page") page: Int, @Query("per_page") per_page: Int) : Observable<Shots>

}