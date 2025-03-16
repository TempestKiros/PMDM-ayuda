package com.example.newtiendadeifinitiva.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.newtiendadefinitiva.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.newtiendadeifinitiva.models.Producto
import com.example.newtiendadeifinitiva.utils.CarritoDBHelper

class FragmentDetalle : Fragment() {

    private lateinit var imgProducto: ImageView
    private lateinit var tvNombreProducto: TextView
    private lateinit var tvDescripcion: TextView
    private lateinit var tvPrecio: TextView
    private lateinit var btnFavorito: Button
    private lateinit var btnAgregarCarrito: Button

    private lateinit var producto: Producto

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detalle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imgProducto = view.findViewById(R.id.imgProducto)
        tvNombreProducto = view.findViewById(R.id.tvNombreProducto)
        tvDescripcion = view.findViewById(R.id.tvDescripcion)
        tvPrecio = view.findViewById(R.id.tvPrecio)
        btnFavorito = view.findViewById(R.id.btnFavorito)
        btnAgregarCarrito = view.findViewById(R.id.btnAgregarCarrito)

        // Se asume que el producto se pasa mediante argumentos y que Producto implementa Parcelable
        producto = arguments?.getParcelable("producto") ?: Producto()

        // Asignar datos al layout
        tvNombreProducto.text = producto.nombre
        tvDescripcion.text = producto.descripcion
        tvPrecio.text = "Precio: \$${producto.precio}"
        if (producto.imagenURL.isNotEmpty()) {
            Glide.with(this)
                .load(producto.imagenURL)
                .into(imgProducto)
        }

        btnFavorito.setOnClickListener { agregarAFavoritos(producto) }
        btnAgregarCarrito.setOnClickListener { agregarAlCarrito(producto) }
    }

    private fun agregarAFavoritos(producto: Producto) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            val db = FirebaseFirestore.getInstance()
            db.collection("usuarios").document(uid)
                .collection("favoritos")
                .document(producto.id)
                .set(producto)
                .addOnSuccessListener {
                    Toast.makeText(context, "Producto agregado a favoritos", Toast.LENGTH_SHORT)
                        .show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun agregarAlCarrito(producto: Producto) {
        val carritoHelper = CarritoDBHelper(requireContext())
        carritoHelper.agregarProductoAlCarrito(producto, 1)
        Toast.makeText(context, "Producto agregado al carrito", Toast.LENGTH_SHORT).show()
    }
}
