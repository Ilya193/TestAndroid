package ru.ikom.catalog.data

import ru.ikom.catalog.domain.ProductDomain
import ru.ikom.products.ProductData

fun ProductData.toProductDomain(): ProductDomain =
    ProductDomain(id, title, description, price, thumbnail)

fun ProductDomain.toProductData(): ProductData =
    ProductData(id, price, thumbnail, title, description)