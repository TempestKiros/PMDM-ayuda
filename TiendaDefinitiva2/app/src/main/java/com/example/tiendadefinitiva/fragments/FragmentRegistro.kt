package com.example.tiendadefinitiva.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tiendadefinitiva.databinding.FragmentRegistroBinding
import com.google.firebase.auth.FirebaseAuth

class FragmentRegistro : Fragment() {

    private lateinit var binding: FragmentRegistroBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistroBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val name = binding.etName.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Save user name to Firestore or Realtime Database
                            // Navigate to login or main activity
                        } else {
                            // Show error
                        }
                    }
            } else {
                // Show error: Fields cannot be empty
            }
        }

        return binding.root
    }
}
