package com.sam.data.retrofit

import com.sam.domain.ApiResponse
import com.sam.domain.Character
import com.sam.domain.Episode
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

    // Get Episode
    @GET("{link}")
    suspend fun getEpisodeByLink(
        @Path("link") link: String
    ): Episode
}