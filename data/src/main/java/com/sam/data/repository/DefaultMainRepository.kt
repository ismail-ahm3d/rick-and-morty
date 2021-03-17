package com.sam.data.repository

import com.sam.data.retrofit.ServiceApi
import com.sam.data.util.Resource
import com.sam.domain.ApiResponse
import com.sam.domain.Character
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val api: ServiceApi
) : MainRepository {

    override suspend fun getAllCharacters(): Resource<ApiResponse> {
        return try {
            val apiResponse = api.getCharacters()
            Resource.Success(apiResponse)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Something went wrong..!!")
        }
    }

    override suspend fun getCharacterById(id: Int): Resource<Character> {
        return try {
            val character = api.getCharacterById(id)
            Resource.Success(character)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Something went wrong..!!")
        }
    }
}