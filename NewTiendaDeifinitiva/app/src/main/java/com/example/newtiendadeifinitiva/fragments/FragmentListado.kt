package com.example.newtiendadeifinitiva.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.findNavController
import com.example.newtiendadefinitiva.R
import com.google.firebase.firestore.FirebaseFirestore
import com.example.newtiendadeifinitiva.adapters.ProductoAdapter
import com.example.newtiendadeifinitiva.models.Producto
import com.example.newtiendadeifinitiva.utils.CarritoDBHelper

class FragmentListado : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductoAdapter
    private lateinit var searchView: SearchView
    private val productosList = mutableListOf<Producto>()
    private lateinit var carritoDBHelper: CarritoDBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_listado, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerViewProductos)
        searchView = view.findViewById(R.id.searchView)
        carritoDBHelper = CarritoDBHelper(requireContext())  // Inicializar la base de datos

        adapter = ProductoAdapter(productosList, { producto ->
            // Navega a FragmentDetalle enviando el objeto producto
            val bundle = Bundle().apply {
                putParcelable("producto", producto)
            }
            findNavController().navigate(R.id.action_fragmentListado_to_fragmentDetalle, bundle)
        }, { producto, cantidad ->
            // Agregar producto al carrito
            agregarAlCarrito(producto, cantidad)
        })

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        loadProductos()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { filterProductos(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { filterProductos(it) }
                return false
            }
        })
    }

    private fun loadProductos() {
        val db = FirebaseFirestore.getInstance()
        db.collection("productos")
            .get()
            .addOnSuccessListener { result ->
                productosList.clear()
                for (document in result) {
                    val producto = document.toObject(Producto::class.java)
                    productosList.add(producto)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun filterProductos(query: String) {
        val filteredList = productosList.filter { it.nombre.contains(query, ignoreCase = true) }
        adapter.updateList(filteredList)
    }

    // Método para agregar un producto al carrito
    fun agregarAlCarrito(producto: Producto, cantidad: Int) {
        carritoDBHelper.agregarProductoAlCarrito(producto, cantidad)
        Toast.makeText(context, "${producto.nombre} agregado al carrito", Toast.LENGTH_SHORT).show()
    }
}
