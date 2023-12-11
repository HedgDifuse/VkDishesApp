package com.hedgdifuse.dishessampleapp.domain

data class Dish(
    val id: String,
    val name: String,
    val description: String?,
    val image: String,
    val price: String
)
