package com.example.greenfinity.data.model


import androidx.annotation.DrawableRes

data class Avatar(
    val name: String,
    @DrawableRes val imageRes: Int
)