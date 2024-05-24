package ru.ikom.database.products

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProductDBO::class], version = 1)
abstract class ProductsDB : RoomDatabase() {
    abstract fun productsDao(): ProductsDao
}