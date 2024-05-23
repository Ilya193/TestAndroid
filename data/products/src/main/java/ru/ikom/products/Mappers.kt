package ru.ikom.products

import ru.ikom.network.products.model.ProductDTO
import ru.ikom.network.products.model.ProductsDTO

internal fun ProductsDTO.toProductsData(): List<ProductData> =
    products.map { it.toProductData() }

internal fun ProductDTO.toProductData(): ProductData =
    ProductData(
        brand,
        category,
        description,
        discountPercentage,
        id,
        images,
        price.toInt(),
        rating,
        stock,
        thumbnail,
        title
    )