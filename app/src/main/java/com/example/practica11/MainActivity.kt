package com.example.practica11

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Declaración de variables para los componentes
    private lateinit var etUser: EditText
    private lateinit var etPass: EditText
    private lateinit var cbRecordar: CheckBox
    private lateinit var btnIngresar: Button
    private lateinit var btnLimpiar: Button

    // Variable para las preferencias compartidas
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Vincular componentes con la vista XML
        etUser = findViewById(R.id.etUsuario)
        etPass = findViewById(R.id.etPassword)
        cbRecordar = findViewById(R.id.cbRecordar)
        btnIngresar = findViewById(R.id.btnIngresar)
        btnLimpiar = findViewById(R.id.btnLimpiar)

        // 2. Inicializar SharedPreferences (Nombre del archivo: "LoginPrefs")
        sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)

        // 3. Verificar si hay datos guardados para cargarlos automáticamente
        val recordar = sharedPreferences.getBoolean("recordar", false)
        if (recordar) {
            etUser.setText(sharedPreferences.getString("usuario", ""))
            etPass.setText(sharedPreferences.getString("password", ""))
            cbRecordar.isChecked = true
        }

        // 4. Configurar botón Ingresar
        btnIngresar.setOnClickListener {
            val usuario = etUser.text.toString()
            val pass = etPass.text.toString()

            // Validación básica (Usuario: admin, Contraseña: 1234)
            if (usuario == "admin" && pass == "1234") {
                val editor = sharedPreferences.edit()

                // Guardar o limpiar datos según el CheckBox
                if (cbRecordar.isChecked) {
                    editor.putString("usuario", usuario)
                    editor.putString("password", pass)
                    editor.putBoolean("recordar", true)
                } else {
                    editor.clear() // Borra los datos si se desmarca
                }
                editor.apply() // Guardar cambios

                // Navegar a la Activity del Menú Lateral
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
                finish() // Cierra el Login para que no se pueda volver atrás
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }

        // 5. Configurar botón Limpiar
        btnLimpiar.setOnClickListener {
            etUser.setText("")
            etPass.setText("")
            cbRecordar.isChecked = false
        }
    }
}