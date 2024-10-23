package com.example.practicapig

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
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
        ocultarElementos(binding)

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
                    quitarDado(binding)
                dado=jugadores[numTurno].tirar()

                    Handler().postDelayed({
                        binding.pasarID.visibility=View.GONE
                        binding.botonTirar.visibility=View.GONE
                    animacion(binding)},100)

                    Handler().postDelayed({
                        if (numtirado!=0)binding.pasarID.visibility=View.VISIBLE
                        binding.botonTirar.visibility=View.VISIBLE
                        binding.puntJUG.text="EL JUGADOR "+(numTurno+1)+" HA SACADO "+dado
                    ponerDado(binding,dado)},1000)
                    numtirado++
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

    private fun ocultarElementos(binding: ActivityMainBinding) {

        binding.botonTirar.visibility = View.GONE
        binding.puntJUG.visibility = View.GONE
        binding.TurnJug.visibility = View.GONE
        binding.pasarID.visibility = View.GONE
        binding.rondaID.visibility = View.GONE
        binding.clasificacionID.visibility = View.GONE
        binding.IDganador.visibility = View.GONE
        binding.dado1.visibility = View.GONE
        binding.dado2.visibility = View.GONE
        binding.dado3.visibility = View.GONE
        binding.dado4.visibility = View.GONE
        binding.dado5.visibility = View.GONE
        binding.dado6.visibility=View.GONE
        binding.dadoRand.visibility=View.GONE
    }
    private fun ponerDado(binding: ActivityMainBinding,dado:Int) {
        when (dado) {
            1 -> binding.dado1.visibility = View.VISIBLE
            2 -> binding.dado2.visibility = View.VISIBLE
            3 -> binding.dado3.visibility = View.VISIBLE
            4 -> binding.dado4.visibility = View.VISIBLE
            5 -> binding.dado5.visibility = View.VISIBLE
            6 -> binding.dado6.visibility = View.VISIBLE
        }
    }
        private fun quitarDado(binding: ActivityMainBinding){

               binding.dado1.visibility = View.GONE
                binding.dado2.visibility = View.GONE
                 binding.dado3.visibility = View.GONE
                 binding.dado4.visibility = View.GONE
                 binding.dado5.visibility = View.GONE
                binding.dado6.visibility = View.GONE

        }

    private fun animacion(binding: ActivityMainBinding){

        //binding.pasarID.visibility=View.GONE
        val carasDelDado = arrayOf(
            R.drawable.dado1,
            R.drawable.dado2,
            R.drawable.dado3,
            R.drawable.dado4,
            R.drawable.dado5,
            R.drawable.dado6
        )

        for (i in 1..6){

        val caraAleatoria = carasDelDado[Random.nextInt(6)]
            Handler().postDelayed({

                val caraAleatoria = carasDelDado[Random.nextInt(6)]

                binding.dadoRand.setImageResource(caraAleatoria)

                binding.dadoRand.visibility = View.VISIBLE

                Handler().postDelayed({
                    binding.dadoRand.visibility = View.GONE
                }, 150L)

            }, (i * 200L))
        }


       // binding.pasarID.visibility=View.VISIBLE
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
