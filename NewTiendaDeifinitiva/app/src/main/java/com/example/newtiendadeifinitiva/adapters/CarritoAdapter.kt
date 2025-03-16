package com.example.newtiendadeifinitiva.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newtiendadefinitiva.R
import com.example.newtiendadeifinitiva.models.CarritoItem

class CarritoAdapter(
    private val items: List<CarritoItem>
) : RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder>() {

    inner class CarritoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombreCarrito)
        val tvCantidad: TextView = itemView.findViewById(R.id.tvCantidadCarrito)
        val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecioCarrito)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarritoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_carrito, parent, false)
        return CarritoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarritoViewHolder, position: Int) {
        val item = items[position]
        holder.tvNombre.text = item.nombre
        holder.tvCantidad.text = "Cantidad: ${item.cantidad}"
        holder.tvPrecio.text = "Precio: \$${item.precio}"
    }

    override fun getItemCount(): Int = items.size
}
