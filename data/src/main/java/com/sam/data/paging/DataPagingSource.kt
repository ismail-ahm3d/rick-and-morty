package com.sam.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sam.data.retrofit.ServiceApi
import com.sam.domain.ApiResponse
import com.sam.domain.Character
import kotlin.Exception

private const val STARTING_INDEX = 1

private const val TAG = "DataPagingSource"

class DataPagingSource(
    private val api: ServiceApi
) : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val pageNumber = params.key ?: STARTING_INDEX

        val response = api.getCharacters(pageNumber)

//        Log.d(TAG, "load: ${response.info.}")

        val info = response.info

        Log.d(TAG, "prev: ${info.prev}")
        Log.d(TAG, "PageNumber: $pageNumber")
        Log.d(TAG, "next: ${info.next}")

        val characters = response.results

        return try {
            LoadResult.Page(
                data = characters,
                prevKey = if (pageNumber == STARTING_INDEX) null else pageNumber - 1,
//                nextKey = if (characters.isEmpty()) null else pageNumber + 1,
//                prevKey = null,
                nextKey = if (info.next.isBlank()) null else pageNumber + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}