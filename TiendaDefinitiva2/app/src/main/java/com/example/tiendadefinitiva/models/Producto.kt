package com.example.tiendadefinitiva.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Entity(tableName = "Producto") // Especificamos que esta clase es una entidad de Room
@Parcelize
data class Producto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Usamos @PrimaryKey para el identificador único
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val imagenUrl: String
) : Parcelable
