package com.example.practica11

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class ListadoFragment : Fragment() {

    val url = "http://10.0.2.2/Moviles/androidConsultaMySql.php"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_listado, container, false)
        val listView = view.findViewById<ListView>(R.id.listViewVehiculos)

        val queue = Volley.newRequestQueue(requireContext())
        queue.add(StringRequest(Request.Method.GET, url,
            { response ->
                if (response != "0") {
                    val lista = ArrayList<String>()
                    try {
                        val jsonArray = JSONArray(response)
                        for (i in 0 until jsonArray.length()) {
                            val row = jsonArray.getJSONObject(i)
                            lista.add("${row.getString("marca")} ${row.getString("modelo")} - $${row.getString("precio")}")
                        }
                        listView.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, lista)
                    } catch (e: Exception) { e.printStackTrace() }
                } else {
                    Toast.makeText(requireContext(), "Sin datos", Toast.LENGTH_SHORT).show()
                }
            },
            { Toast.makeText(requireContext(), "Error de conexión", Toast.LENGTH_SHORT).show() }
        ))

        return view
    }
}