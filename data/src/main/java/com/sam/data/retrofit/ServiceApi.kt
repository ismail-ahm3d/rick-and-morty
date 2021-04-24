package com.sam.data.retrofit

import com.sam.domain.ApiResponse
import com.sam.domain.Character
import com.sam.domain.Episode
import com.sam.domain.network.NetworkCharacter
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int = 1
    ): ApiResponse

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): NetworkCharacter

    // Get Episode
    @GET("{link}")
    suspend fun getEpisodeByLink(
        @Path("link") link: String
    ): Episode
}