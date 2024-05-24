package ru.ikom.database.products

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {

    @Query("SElECT * FROM products")
    suspend fun fetchProducts(): List<ProductDBO>

    @Query("SElECT * FROM products")
    fun fetchProductsWithFlow(): Flow<List<ProductDBO>>

    @Insert
    suspend fun add(product: ProductDBO)

    @Delete
    suspend fun delete(product: ProductDBO)
}