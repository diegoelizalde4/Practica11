package com.example.practica11

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class InicioFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflamos el layout "fragment_inicio" para mostrar la pantalla de bienvenida
        return inflater.inflate(R.layout.fragment_inicio, container, false)
    }
}