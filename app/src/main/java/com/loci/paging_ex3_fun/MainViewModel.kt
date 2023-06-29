package com.loci.paging_ex3_fun

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.loci.paging_ex3_fun.data.Data
import com.loci.paging_ex3_fun.network.PassengerApi
import com.loci.paging_ex3_fun.network.RetrofitInstance
import kotlinx.coroutines.flow.Flow

class MainViewModel : ViewModel() {

    private val api = RetrofitInstance.getInstance().create(PassengerApi::class.java)

    val items: Flow<PagingData<Data>> = Pager(
        config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            MyPagingSource(api)
        }
    )
        .flow
        .cachedIn(viewModelScope)

}