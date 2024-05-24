package ru.ikom.basket.presentation

import ru.ikom.common.BaseRouter

interface BasketRouter : BaseRouter {
    fun openDetails(data: String)
}