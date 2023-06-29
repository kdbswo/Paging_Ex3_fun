package com.loci.paging_ex3_fun

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.loci.paging_ex3_fun.data.Data
import com.loci.paging_ex3_fun.network.PassengerApi
import kotlinx.coroutines.delay

private const val STARTING_KEY = 1

class MyPagingSource(
    private val passengerApi: PassengerApi
) : PagingSource<Int, Data>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        val page = params.key ?: STARTING_KEY

        val response = passengerApi.getData(page, params.loadSize)

        val data = response.body()?.data

        if (page != 1) {
            delay(3000)
        }

        if (data == null) {
            return LoadResult.Page(
                data = listOf(),
                prevKey = null,
                nextKey = null
            )
        } else {
            return LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = page + (params.loadSize / 30)
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {

        Log.d("getRefresh", "start")

        val anchorPosition = state.anchorPosition

        return anchorPosition?.let { anchorPosition

            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)

        }
    }
}