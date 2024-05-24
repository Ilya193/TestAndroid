package ru.ikom.catalog.presentation

import kotlinx.serialization.Serializable

@Serializable
data class ProductUi(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val thumbnail: String,
    val added: Boolean
)