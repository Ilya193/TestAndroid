package ru.ikom.products

import ru.ikom.database.products.ProductDBO
import ru.ikom.network.products.model.ProductDTO
import ru.ikom.network.products.model.ProductsDTO

internal fun ProductsDTO.toProductsData(): List<ProductData> =
    products.map { it.toProductData() }

internal fun ProductDTO.toProductData(): ProductData =
    ProductData(id, price.toInt(), thumbnail, title, description)

internal fun ProductDBO.toProductData(): ProductData =
    ProductData(id, price, thumbnail, title, description)

internal fun ProductData.toProductDBO(): ProductDBO =
    ProductDBO(id, title, description, price, thumbnail)