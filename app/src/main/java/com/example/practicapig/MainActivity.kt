package com.example.practicapig

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.practicapig.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.botonTirar.visibility=View.GONE
        binding.puntJUG.visibility=View.GONE
        binding.TurnJug.visibility=View.GONE
        binding.pasarID.visibility=View.GONE
        binding.rondaID.visibility=View.GONE
        binding.clasificacionID.visibility=View.GONE
        binding.IDganador.visibility=View.GONE
        var texto = ""
        var num: Int=0
        var numTurno=0
        var jugadores = ArrayList<Jugador>()
        var rondas:Int=4

        binding.eleccionNum.setOnCheckedChangeListener { _, id ->
            val radioButton = findViewById<RadioButton>(id)
            texto = radioButton.text.toString()
        }

        binding.salirID.setOnClickListener {
            num = pulsarBoton(texto, num)
            for (i in 1..num) {
                jugadores.add(Jugador("Jugador "+i))
            }
            var dado:Int=0
            var numtirado=0
            var turno=1
            binding.TurnJug.text= "Turno del "+jugadores.get(0).nombre
            binding.rondaID.visibility=View.VISIBLE
            binding.clasificacionID.visibility=View.VISIBLE
            binding.rondaID.text= "RONDA: "+turno

                binding.botonTirar.setOnClickListener {

                dado=jugadores[numTurno].tirar()
                binding.puntJUG.text="EL JUGADOR "+(numTurno+1)+" HA SACADO "+dado
                numtirado++

                if (numtirado!=0)binding.pasarID.visibility=View.VISIBLE

                if (dado==1) {
                    numTurno++
                    if (numTurno==num){
                        numTurno=0
                        turno++
                        binding.rondaID.text= "RONDA: "+turno
                    }
                    binding.TurnJug.text= "Turno del Jugador "+(numTurno+1)
                    binding.pasarID.visibility=View.GONE
                    numtirado=0
                }
                    //

                binding.pasarID.setOnClickListener{
                    numTurno++
                    if (numTurno==num) {
                        numTurno=0
                        turno++
                        binding.rondaID.text= "RONDA: "+turno
                    }
                    binding.pasarID.visibility=View.GONE
                    binding.TurnJug.text= "Turno del Jugador "+(numTurno+1)
                    numtirado=0
                    clasificacion(binding,jugadores);

                    if (turno==rondas+1){
                        ganador(jugadores)


                    }
                }

                clasificacion(binding, jugadores)
                    if (turno==rondas+1){
                        ganador(jugadores)
                    }

            }






        }




    }

    private fun ganador(jugadores: ArrayList<Jugador>) {
        binding.pasarID.visibility = View.GONE
        binding.TurnJug.visibility = View.GONE
        binding.botonTirar.visibility = View.GONE
        binding.rondaID.visibility = View.GONE
        binding.puntJUG.visibility = View.GONE
        clasificacion(binding, jugadores)
        binding.IDganador.visibility = View.VISIBLE
        //jugadores.sortedBy { it.puntuacion }
        jugadores.sortByDescending { it.puntuacion }
        binding.IDganador.text = "EL GANADOR ES: " + jugadores.get(0).nombre

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
                binding.puntJUG.visibility=View.VISIBLE
                binding.TurnJug.visibility=View.VISIBLE
                Toast.makeText(this, "Número seleccionado: " + num1, Toast.LENGTH_SHORT).show()

            }
        } else {
            Toast.makeText(this, "Debes seleccionar una opción", Toast.LENGTH_SHORT).show()
        }
        return num1
    }

    private fun clasificacion(binding: ActivityMainBinding, jugadores: ArrayList<Jugador>){
        var mensaje:String=""
        for (i in 0 .. jugadores.size-1){

            mensaje=mensaje.plus("\n JUGADOR "+(i+1)+": "+jugadores[i].puntuacion)

        }
        binding.clasificacionID.text=mensaje

    }
    private fun ganador(binding: ActivityMainBinding, jugadores: ArrayList<Jugador>){


    }

    class Jugador(s: String) {

         var puntuacion:Int=0
         var nombre:String=s

        public fun tirar(): Int {
        var dado=Random.nextInt(1,7)
            when(dado) {
                1 -> puntuacion=0
                2 -> puntuacion=puntuacion+2
                3 -> puntuacion=puntuacion+3
                4 -> puntuacion=puntuacion+4
                5 -> puntuacion=puntuacion+5
                6 -> puntuacion=puntuacion+6
            }
          return dado

        }

    }

}
