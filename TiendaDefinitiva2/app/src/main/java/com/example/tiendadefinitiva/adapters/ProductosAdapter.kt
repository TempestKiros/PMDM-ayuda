package com.example.tiendadefinitiva.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tiendadefinitiva.databinding.ItemProductoBinding
import com.example.tiendadefinitiva.models.Producto

class ProductosAdapter(private val productos: List<Producto>) : RecyclerView.Adapter<ProductosAdapter.ProductoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {

        val binding = ItemProductoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.bind(producto)
    }

    override fun getItemCount(): Int = productos.size

    inner class ProductoViewHolder(private val binding: ItemProductoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(producto: Producto) {
            binding.tvProductName.text = producto.nombre
            binding.tvProductPrice.text = "$${producto.precio}"
            // Load image using Glide or Picasso
        }
    }
}
