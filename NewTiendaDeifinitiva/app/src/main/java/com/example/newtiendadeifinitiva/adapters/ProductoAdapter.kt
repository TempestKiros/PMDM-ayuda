package com.example.newtiendadeifinitiva.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newtiendadefinitiva.R
import com.example.newtiendadeifinitiva.models.Producto
import com.bumptech.glide.Glide

class ProductoAdapter(
    private var productos: List<Producto>,
    private val onProductoClick: (Producto) -> Unit,
    private val onAgregarAlCarrito: (Producto, Int) -> Unit
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.bind(producto)
    }

    override fun getItemCount(): Int = productos.size

    fun updateList(newList: List<Producto>) {
        productos = newList
        notifyDataSetChanged()
    }

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.tvNombreProducto)
        private val precioTextView: TextView = itemView.findViewById(R.id.tvPrecio)
        private val imagenImageView: ImageView = itemView.findViewById(R.id.imgProducto)
        private val agregarCarritoButton: Button = itemView.findViewById(R.id.btnAgregarCarrito)

        fun bind(producto: Producto) {
            nombreTextView.text = producto.nombre
            precioTextView.text = "$${producto.precio}"

            // Aquí se usa Glide para cargar la imagen
            Glide.with(itemView.context)
                .load(producto.imagenUrl)  // Usar imagenUrl en lugar de imagen
                .into(imagenImageView)

            itemView.setOnClickListener {
                onProductoClick(producto) // Llamar a la función de clic del producto
            }

            agregarCarritoButton.setOnClickListener {
                val cantidad = 1 // Podrías agregar un selector de cantidad si es necesario
                onAgregarAlCarrito(producto, cantidad)
            }
        }
    }
}
