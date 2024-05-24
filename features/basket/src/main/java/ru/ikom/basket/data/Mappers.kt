package ru.ikom.basket.data

import ru.ikom.basket.domain.ProductDomain
import ru.ikom.products.ProductData

fun ProductData.toProductDomain(): ProductDomain =
    ProductDomain(id, title, description, price, thumbnail)

fun ProductDomain.toProductData(): ProductData =
    ProductData(id, price, thumbnail, title, description)