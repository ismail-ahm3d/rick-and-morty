package com.sam.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sam.data.retrofit.ServiceApi
import com.sam.domain.ApiResponse
import com.sam.domain.Character
import com.sam.domain.network.NetworkCharacter
import retrofit2.HttpException
import java.io.IOException
import kotlin.Exception

private const val STARTING_INDEX = 1

private const val TAG = "DataPagingSource"

class DataPagingSource(
    private val api: ServiceApi,
) : PagingSource<Int, NetworkCharacter>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NetworkCharacter> {
        val pageNumber = params.key ?: STARTING_INDEX

        return try {
            val response = api.getCharacters(pageNumber)

            val info = response.info

            val characters = response.results

            LoadResult.Page(
                data = characters,
                prevKey = if (pageNumber == STARTING_INDEX) null else pageNumber - 1,
//                nextKey = if (characters.isEmpty()) null else pageNumber + 1,
//                prevKey = null,
                nextKey = if (info.next.isBlank()) null else pageNumber + 1,
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}