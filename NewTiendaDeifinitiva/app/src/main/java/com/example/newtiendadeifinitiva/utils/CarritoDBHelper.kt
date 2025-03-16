package com.example.newtiendadeifinitiva.utils

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.newtiendadeifinitiva.models.CarritoItem
import com.example.newtiendadeifinitiva.models.Producto

class CarritoDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "carritoDB.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_CARRITO = "carrito"
        private const val COLUMN_ID = "id"
        private const val COLUMN_PRODUCTO_ID = "producto_id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_PRECIO = "precio"
        private const val COLUMN_CANTIDAD = "cantidad"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_CARRITO (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_PRODUCTO_ID TEXT, " +
                "$COLUMN_NOMBRE TEXT, " +
                "$COLUMN_PRECIO REAL, " +
                "$COLUMN_CANTIDAD INTEGER)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CARRITO")
        onCreate(db)
    }

    fun agregarProductoAlCarrito(producto: Producto, cantidad: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_PRODUCTO_ID, producto.id)
            put(COLUMN_NOMBRE, producto.nombre)
            put(COLUMN_PRECIO, producto.precio)
            put(COLUMN_CANTIDAD, cantidad)
        }
        db.insert(TABLE_CARRITO, null, values)
        db.close()
    }

    fun getAllItems(): List<CarritoItem> {
        val items = mutableListOf<CarritoItem>()
        val db = readableDatabase
        val cursor = db.query(TABLE_CARRITO, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val productoId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCTO_ID))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE))
                val precio = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRECIO))
                val cantidad = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CANTIDAD))
                val item = CarritoItem(productoId, nombre, precio, cantidad)
                items.add(item)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return items
    }

    fun vaciarCarrito() {
        val db = writableDatabase
        db.delete(TABLE_CARRITO, null, null)
        db.close()
    }
}
