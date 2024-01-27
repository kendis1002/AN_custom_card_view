package com.kendis.cardview

data class CryptoCardData(
    val name: String,
    val value: Float,
    val valueChange: Int,
    val currentTotal: Long,
    val icon: Int
)
