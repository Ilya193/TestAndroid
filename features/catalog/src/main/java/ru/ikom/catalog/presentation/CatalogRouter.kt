package ru.ikom.catalog.presentation

import ru.ikom.common.BaseRouter

interface CatalogRouter : BaseRouter {
    fun openDetails(data: String)
}