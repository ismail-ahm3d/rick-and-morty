package com.sam.data.repository

import com.sam.data.retrofit.ServiceApi
import com.sam.data.util.Constants.BASE_URL
import com.sam.data.util.Resource
import com.sam.domain.ApiResponse
import com.sam.domain.Character
import javax.inject.Inject
import kotlin.random.Random

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

    override suspend fun getRandomCharacters(): Resource<ApiResponse> {
        return try {
            val randomNumber = Random(System.nanoTime()).nextInt(1, 34)
            val apiResponse = api.getCharacters(randomNumber)
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