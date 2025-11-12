package com.example.practica11

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class GestionFragment : Fragment() {

    // Usar "http://10.0.2.2/..." para emulador o la IP de tu PC para dispositivo físico
    val urlBase = "http://10.0.2.2/Moviles/"

    lateinit var etMarca: EditText
    lateinit var etModelo: EditText
    lateinit var etColor: EditText
    lateinit var etHp: EditText
    lateinit var etPrecio: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gestion, container, false)

        etMarca = view.findViewById(R.id.etMarca)
        etModelo = view.findViewById(R.id.etModelo)
        etColor = view.findViewById(R.id.etColor)
        etHp = view.findViewById(R.id.etHp)
        etPrecio = view.findViewById(R.id.etPrecio)

        val queue = Volley.newRequestQueue(requireContext())

        view.findViewById<Button>(R.id.btnAgregar).setOnClickListener {
            val url = "${urlBase}androidInsercionMySql.php?marca=${etMarca.text}&modelo=${etModelo.text}&color=${etColor.text}&hp=${etHp.text}&precio=${etPrecio.text}"
            queue.add(StringRequest(Request.Method.GET, url, { r -> msg(r) }, { e -> msg("Error: ${e.message}") }))
        }

        view.findViewById<Button>(R.id.btnBuscar).setOnClickListener {
            val url = "${urlBase}androidBusquedaMySql.php?marca=${etMarca.text}&modelo=${etModelo.text}"
            queue.add(StringRequest(Request.Method.GET, url, { response ->
                if (response == "0") msg("No encontrado")
                else {
                    try {
                        val json = JSONArray(response).getJSONObject(0)
                        etColor.setText(json.getString("color"))
                        etHp.setText(json.getString("caballos_fuerza"))
                        etPrecio.setText(json.getString("precio"))
                    } catch (e: Exception) { e.printStackTrace() }
                }
            }, { msg("Error de red") }))
        }

        // Implementa Actualizar y Eliminar similar a Agregar cambiando el PHP

        return view
    }

    fun msg(texto: String) {
        Toast.makeText(requireContext(), texto, Toast.LENGTH_SHORT).show()
    }
}