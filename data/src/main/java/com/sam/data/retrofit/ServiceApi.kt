package com.sam.data.retrofit

import com.sam.domain_module.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ServiceApi {

    @GET("character")
    suspend fun getCharacters(): ApiResponse

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): Character
}