package com.example.tiendadefinitiva.database

import androidx.room.Dao
import androidx.room.Query
import com.example.tiendadefinitiva.models.Producto

@Dao
interface ProductoDao {

    @Query("SELECT * FROM Producto")
    suspend fun obtenerProductos(): List<Producto>
}
