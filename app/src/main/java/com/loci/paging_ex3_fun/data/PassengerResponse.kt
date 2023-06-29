package com.loci.paging_ex3_fun.data

data class PassengerResponse(
    val `data`: List<Data>,
    val totalPages: Int,
    val totalPassengers: Int
)