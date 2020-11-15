package com.mtsa.resistcalc

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.mtsa.resistcalc.fbk.ResultadoFBK
import com.mtsa.resistcalc.fpk.ResultadoFPK
import org.jetbrains.anko.alert


class Entrada : AppCompatActivity(), View.OnClickListener {

    private lateinit var edtxEntradas: EditText
    private lateinit var btCalcular: Button
    private lateinit var btExemplo: Button
    private lateinit var exemplo: String
    private lateinit var OPERATION: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        OPERATION = intent.getStringExtra("OP")
        initViews(OPERATION)
    }

    private fun initViews(opt: String) {

        when (opt) {
            "FBK" -> {
                setContentView(R.layout.act_entrada_fbk)
                edtxEntradas = findViewById(R.id.actENTRY_edtxEntradas_fbk)
                btCalcular = findViewById(R.id.actENTRY_btCalcular_fbk)
                btExemplo = findViewById(R.id.actENTRY_btExemplo_fbk)

                btCalcular.setOnClickListener(this)
                btExemplo.setOnClickListener(this)

                exemplo =
                    "12,4; 12,4; 12,4; 12,6; 13,5; 13,5; 13,9; 14,2; 14,2; 15,1; 15,4; 16,2; 16,4; 17,4"

            }
            "FPK" -> {
                setContentView(R.layout.act_entrada_fpk)
                edtxEntradas = findViewById(R.id.actENTRY_edtxEntradas_fpk)
                btCalcular = findViewById(R.id.actENTRY_btCalcular_fpk)
                btExemplo = findViewById(R.id.actENTRY_btExemplo_fpk)

                btCalcular.setOnClickListener(this)
                btExemplo.setOnClickListener(this)

                exemplo = "12,4; 12,4; 12,6; 13,5; 15,4; 16,4; 17,4"

            }
        }

    }

    private fun getValues(entrada: String): FloatArray {

        val lista = ArrayList<String>(entrada.trim().split(";", "\n").sorted())
        lista.removeAll(listOf("", null))

        val amostras = FloatArray(lista.count())
        for ((index, value) in lista.withIndex()) {
            if (value.contains(","))
                amostras[index] = value.replace(",", ".").toFloat()
            else
                amostras[index] = value.toFloat()
        }
        return amostras.sortedArray()

    }

    private fun showResults(user_input: Boolean) {

        println(OPERATION)
        val input = edtxEntradas.text.toString()

        when (OPERATION) {
            "FBK" -> {
                startActivity(
                    Intent(this, ResultadoFBK::class.java).putExtra(
                        "AMOSTRAS",
                        getValues(if (user_input) input else exemplo)
                    )
                )
            }
            "FPK" -> {
                startActivity(
                    Intent(this, ResultadoFPK::class.java).putExtra(
                        "AMOSTRAS",
                        getValues(if (user_input) input else exemplo)
                    )
                )
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            btCalcular -> {
                val input = edtxEntradas.text.toString()
                val quantidade = getValues(input).count()
                if (quantidade > 5) {
                    alert("Você digitou $quantidade elementos.\nEsse é o tamanho correto da sua amostra?") {
                        title = "Só confirmando..."
                        positiveButton("Está correto") { showResults(true) }
                        negativeButton("Preciso corrigir") {}
                    }.show()
                } else {
                    alert("Para um cálculo correto, você precisa informar pelo menos 6 valores...") {
                        title = "ERRO!"
                        positiveButton("CORRIGIR") {}
                    }.show()
                }
            }
            btExemplo -> showResults(false)
        }
    }
}
