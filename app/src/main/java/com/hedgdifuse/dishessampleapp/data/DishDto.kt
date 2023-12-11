package com.hedgdifuse.dishessampleapp.data

data class DishDto(
    val id: String,
    val name: String,
    val description: String?,
    val image: String,
    val price: Int
)
