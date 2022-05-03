package com.lft.hear4me

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.net.URL


interface ApiInterface {
    @GET("analyse_audio")
    fun getData(
        @Query("audio_file") audioURL:URL,
        @Query("filename") filename:String
    ): Call<apiCallReturnItem>


}