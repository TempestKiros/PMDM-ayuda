package com.example.newtiendadeifinitiva.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.Parceler

@Parcelize

data class Producto(
    val id: String,           // Campo id único
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val categoria: String,
    val imagenUrl: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    companion object : Parceler<Producto> {

        override fun Producto.write(parcel: Parcel, flags: Int) {
            parcel.writeString(id)
            parcel.writeString(nombre)
            parcel.writeString(descripcion)
            parcel.writeDouble(precio)
            parcel.writeString(categoria)
            parcel.writeString(imagenUrl)
        }

        override fun create(parcel: Parcel): Producto {
            return Producto(parcel)
        }
    }
}
