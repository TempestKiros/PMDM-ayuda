package com.example.newtiendadeifinitiva.models

data class CarritoItem(
    val productoId: String = "",
    val nombre: String = "",
    val precio: Double = 0.0,
    val cantidad: Int = 1
)
