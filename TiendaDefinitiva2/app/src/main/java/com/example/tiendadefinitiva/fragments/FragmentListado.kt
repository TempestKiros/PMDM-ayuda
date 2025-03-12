package com.example.tiendadefinitiva.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tiendadefinitiva.databinding.FragmentListadoBinding
import com.example.tiendadefinitiva.adapters.ProductosAdapter
import com.example.tiendadefinitiva.models.Producto
import com.google.firebase.firestore.FirebaseFirestore

class FragmentListado : Fragment() {

    private lateinit var binding: FragmentListadoBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var adapter: ProductosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListadoBinding.inflate(inflater, container, false)
        firestore = FirebaseFirestore.getInstance()

        val productosRef = firestore.collection("productos")
        productosRef.get().addOnSuccessListener { result ->
            val productos = mutableListOf<Producto>()
            for (document in result) {
                val producto = document.toObject(Producto::class.java)
                productos.add(producto)
            }

            adapter = ProductosAdapter(productos)
            binding.rvProducts.layoutManager = LinearLayoutManager(context)
            binding.rvProducts.adapter = adapter
        }

        return binding.root
    }
}
