package com.example.tiendadefinitiva.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tiendadefinitiva.databinding.FragmentCarritoBinding
import com.example.tiendadefinitiva.adapters.ProductosAdapter
import com.example.tiendadefinitiva.models.Producto

class FragmentCarrito : Fragment() {

    private lateinit var binding: FragmentCarritoBinding
    private lateinit var adapter: ProductosAdapter
    private var carrito = mutableListOf<Producto>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCarritoBinding.inflate(inflater, container, false)

        // Get cart items (from Firestore or SQLite)
        // Example: carrito = loadCartItems()

        adapter = ProductosAdapter(carrito)
        binding.rvCarrito.layoutManager = LinearLayoutManager(context)
        binding.rvCarrito.adapter = adapter

        val total = carrito.sumOf { it.precio }
        binding.tvTotal.text = "Total: $${total}"

        binding.btnCheckout.setOnClickListener {
            // Proceed to checkout and save order in Firestore
        }

        return binding.root
    }
}
