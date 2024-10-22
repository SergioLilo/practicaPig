package com.example.practicapig

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.practicapig.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.botonTirar.visibility=View.GONE
        var salir:Boolean=false
        var texto = ""
        var num: Int=0
        val jugadores = ArrayList<Jugador>()

        binding.eleccionNum.setOnCheckedChangeListener { _, id ->
            val radioButton = findViewById<RadioButton>(id)
            texto = radioButton.text.toString()
        }

        binding.salirID.setOnClickListener {
            num = pulsarBoton(texto, num)
            for (i in 1..num) {
                jugadores.add(Jugador())
            }


            binding.botonTirar.setOnClickListener {


            }


        }




    }

    private fun pulsarBoton(texto: String, num: Int): Int {
        var num1 = num
        if (texto.isNotEmpty()) {
            num1 = texto.toInt()
            if (num1 != null) {


                // Ocultamos los elementos
                binding.eleccionNum.visibility = View.GONE
                binding.salirID.visibility = View.GONE
                binding.textView.visibility = View.GONE

                binding.botonTirar.visibility=View.VISIBLE
                Toast.makeText(this, "Número seleccionado: " + num1, Toast.LENGTH_SHORT).show()

            }
        } else {
            Toast.makeText(this, "Debes seleccionar una opción", Toast.LENGTH_SHORT).show()
        }
        return num1
    }

    class Jugador{

         var puntuacion:Int=0
    }

}
