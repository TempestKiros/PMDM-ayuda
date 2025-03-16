package com.example.newtiendadeifinitiva.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.findNavController
import com.example.newtiendadefinitiva.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import com.example.newtiendadeifinitiva.adapters.ProductoAdapter
import com.example.newtiendadeifinitiva.models.Producto

class FragmentFavoritos : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductoAdapter
    private val favoritosList = mutableListOf<Producto>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
    return inflater.inflate(R.layout.fragment_favoritos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerViewFavoritos)
        adapter = ProductoAdapter(favoritosList) { producto ->
                // Navega a FragmentDetalle al hacer clic en un producto favorito
                val bundle = Bundle().apply {
            putParcelable("producto", producto)
        }
            findNavController().navigate(R.id.action_fragmentFavoritos_to_fragmentDetalle, bundle)
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        loadFavoritos()
    }

    private fun loadFavoritos() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            val db = FirebaseFirestore.getInstance()
            db.collection("usuarios").document(uid)
                    .collection("favoritos")
                    .get()
                    .addOnSuccessListener { result ->
                    favoritosList.clear()
                for (document in result) {
                    val producto = document.toObject(Producto::class.java)
                    favoritosList.add(producto)
                }
                adapter.notifyDataSetChanged()
            }
                .addOnFailureListener { exception ->
                    Toast.makeText(context, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
