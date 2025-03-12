package com.example.tiendadefinitiva.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tiendadefinitiva.databinding.FragmentFavoritosBinding
import com.example.tiendadefinitiva.adapters.ProductosAdapter
import com.example.tiendadefinitiva.models.Producto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FragmentFavoritos : Fragment() {

    private lateinit var binding: FragmentFavoritosBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var adapter: ProductosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritosBinding.inflate(inflater, container, false)
        firestore = FirebaseFirestore.getInstance()

        val user = FirebaseAuth.getInstance().currentUser
        val favoritosRef = firestore.collection("favoritos").document(user?.uid ?: "").collection("productos")
        favoritosRef.get().addOnSuccessListener { result ->
            val productos = mutableListOf<Producto>()
            for (document in result) {
                val producto = document.toObject(Producto::class.java)
                productos.add(producto)
            }

            adapter = ProductosAdapter(productos)
            binding.rvFavorites.layoutManager = LinearLayoutManager(context)
            binding.rvFavorites.adapter = adapter
        }

        return binding.root
    }
}
