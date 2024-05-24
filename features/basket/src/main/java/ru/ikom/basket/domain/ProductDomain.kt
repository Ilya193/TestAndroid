package ru.ikom.basket.domain

data class ProductDomain(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val thumbnail: String,
)