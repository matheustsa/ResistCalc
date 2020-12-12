package com.mtsa.resistcalc

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mtsa.resistcalc.fbk.ResultadoFBK
import com.mtsa.resistcalc.fpk.ResultadoFPK
import org.jetbrains.anko.alert
import org.jetbrains.anko.collections.forEachWithIndex


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
        lista.forEachWithIndex { index, value ->
            amostras[index] = value.replace(",", ".").toFloat()
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
                var amostras = floatArrayOf()
                try {
                    amostras = getValues(edtxEntradas.text.toString())
                    if (amostras.count() > 4) {
                        alert("Você digitou ${amostras.count()} elementos.\nEsse é o tamanho correto da sua amostra?") {
                            title = "Só confirmando..."
                            positiveButton("Está correto") { showResults(true) }
                            negativeButton("Preciso corrigir") {}
                        }.show().apply {
                            getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(ContextCompat.getColor(context, R.color.forest_green))
                            getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(Color.RED)
                        }
                    } else {
                        alert("Para um cálculo correto, você precisa informar pelo menos 6 valores...") {
                            title = "ERRO!"
                            positiveButton("CORRIGIR") {}
                        }.show()
                    }
                } catch (e: NumberFormatException) {
                    alert("Siga o modelo proposto na dica deste campo ou clique no botão '?' para ver um exemplo."){
                        title = "Valores inválidos!"
                        positiveButton("Ok"){}
                    }.show()
                }
            }
//            btExemplo -> showResults(false)
            btExemplo -> edtxEntradas.setText(exemplo)
        }
    }
}
