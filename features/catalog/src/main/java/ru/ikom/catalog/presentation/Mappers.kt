package ru.ikom.catalog.presentation

import ru.ikom.catalog.R
import ru.ikom.catalog.domain.ErrorType
import ru.ikom.catalog.domain.ProductDomain

fun ProductDomain.toProductUi(): ProductUi = ProductUi(
    brand,
    category,
    description,
    discountPercentage,
    id,
    images,
    price,
    rating,
    stock,
    thumbnail,
    title
)

fun ErrorType.getMessage(): Int {
    return when (this) {
        ErrorType.NO_CONNECTION -> R.string.no_connection
        ErrorType.GENERIC_ERROR -> R.string.generic_error
    }
}