package ru.ikom.network.products

import retrofit2.http.GET
import ru.ikom.network.products.model.ProductsDTO

interface ProductsService {

    @GET("products")
    suspend fun fetchProducts(): ProductsDTO
}