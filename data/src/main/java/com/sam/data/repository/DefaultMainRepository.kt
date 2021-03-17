package com.sam.data.repository

import com.sam.data.retrofit.ServiceApi
import com.sam.data.util.Resource
import com.sam.domain_module.ApiResponse
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

//    override suspend fun getCharacterById(id: Int): Resource<Character> {
//        TODO("Not yet implemented")
//    }
}