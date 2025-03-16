package com.example.newtiendadeifinitiva.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newtiendadefinitiva.R
import com.google.firebase.firestore.FirebaseFirestore
import com.example.newtiendadeifinitiva.adapters.CarritoAdapter
import com.example.newtiendadeifinitiva.models.CarritoItem
import com.example.newtiendadeifinitiva.utils.CarritoDBHelper
import com.google.firebase.auth.FirebaseAuth

class FragmentCarrito : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CarritoAdapter
    private lateinit var tvTotal: TextView
    private lateinit var btnCheckout: Button
    private val carritoList = mutableListOf<CarritoItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_carrito, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerViewCarrito)
        tvTotal = view.findViewById(R.id.tvTotal)
        btnCheckout = view.findViewById(R.id.btnCheckout)

        adapter = CarritoAdapter(carritoList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        loadCarrito()

        btnCheckout.setOnClickListener { checkout() }
    }

    private fun loadCarrito() {
        val carritoHelper = CarritoDBHelper(requireContext())
        carritoList.clear()
        carritoList.addAll(carritoHelper.getAllItems()) // Implementa este método en CarritoDBHelper
        adapter.notifyDataSetChanged()
        calculateTotal()
    }

    private fun calculateTotal() {
        val total = carritoList.sumByDouble { it.precio * it.cantidad }
        tvTotal.text = "Total: \$${total}"
    }

    private fun checkout() {
        val db = FirebaseFirestore.getInstance()
        val compra = hashMapOf(
            "items" to carritoList.map { mapOf(
                "producto_id" to it.productoId,
                "nombre" to it.nombre,
                "precio" to it.precio,
                "cantidad" to it.cantidad
            ) },
            "total" to carritoList.sumByDouble { it.precio * it.cantidad },
            "usuario" to FirebaseAuth.getInstance().currentUser?.uid
        )
        db.collection("compras")
            .add(compra)
            .addOnSuccessListener {
                // Vaciar el carrito luego de la compra
                val carritoHelper = CarritoDBHelper(requireContext())
                carritoHelper.vaciarCarrito()
                Toast.makeText(context, "Compra realizada con éxito", Toast.LENGTH_SHORT).show()
                loadCarrito()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
