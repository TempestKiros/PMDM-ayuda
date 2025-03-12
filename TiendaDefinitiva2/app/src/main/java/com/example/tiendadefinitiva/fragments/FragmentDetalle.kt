package com.example.tiendadefinitiva.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tiendadefinitiva.databinding.FragmentDetalleBinding
import com.example.tiendadefinitiva.models.Producto

class FragmentDetalle : Fragment() {

    private lateinit var binding: FragmentDetalleBinding
    private lateinit var producto: Producto

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetalleBinding.inflate(inflater, container, false)

        // Get the product from arguments or view model
        producto = arguments?.getParcelable("producto") ?: return binding.root

        // Set the product details
        binding.tvProductName.text = producto.nombre
        binding.tvProductDescription.text = producto.descripcion
        binding.tvProductPrice.text = "$${producto.precio}"

        binding.btnAddToCart.setOnClickListener {
            // Add product to the cart in Firestore or SQLite
        }

        return binding.root
    }
}
