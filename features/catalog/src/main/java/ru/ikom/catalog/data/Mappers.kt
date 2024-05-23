package ru.ikom.catalog.data

import ru.ikom.catalog.domain.ProductDomain
import ru.ikom.products.ProductData

fun ProductData.toProductDomain(): ProductDomain =
    ProductDomain(
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