package ru.ikom.basket.presentation

import ru.ikom.basket.domain.ProductDomain

fun ProductDomain.toProductUi(): ProductUi =
    ProductUi(id, title, description, price, thumbnail)

fun ProductUi.toProductDomain(): ProductDomain =
    ProductDomain(id, title, description, price, thumbnail)