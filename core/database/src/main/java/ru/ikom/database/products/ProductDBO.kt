package ru.ikom.database.products

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductDBO(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val thumbnail: String,
)