package com.hedgdifuse.dishessampleapp.domain.mapper

import com.hedgdifuse.dishessampleapp.data.DishDto
import com.hedgdifuse.dishessampleapp.domain.Dish

fun DishDto.toDomainEntity() = Dish(
    id,
    name,
    description,
    image,
    price.toString()
)
