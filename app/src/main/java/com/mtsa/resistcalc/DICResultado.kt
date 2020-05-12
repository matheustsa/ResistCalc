package com.mtsa.resistcalc

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DICResultado : AppCompatActivity() {

    private lateinit var txvRes: TextView
    private lateinit var txvTrat: TextView

    private var string = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_dic__resultado)

        val DIC = intent.getSerializableExtra("DIC") as DIC
        val lista = DIC.sLIST

        txvRes = findViewById(R.id.actDIC_txvResultado)
        txvTrat = findViewById(R.id.actDIC_txvTratamentos)

        txvRes.text = "k: ${DIC.sK}\nn: ${DIC.sN}\nalfa: ${DIC.sALFA}"

        lista.forEach {
            var media = listOf<Double>(it.average())
        }

        val media = lista[0].average()
        val desvP = 0
        val varia = 0
        val cv = (media / desvP) * 100  // coeficientede variação
        val mediaGlobal = 0

        txvRes.text = txvRes.text.toString() + ""
        txvTrat.text = getValues(lista)



    }
    
    private fun getValues(lista: List<FloatArray>): String {
        for ((index, value) in lista.withIndex())
            string += "T${index+1} - " + value.asList().toString() + "\n"

        return string

    }
}
