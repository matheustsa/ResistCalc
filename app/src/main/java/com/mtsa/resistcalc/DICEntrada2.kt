package com.mtsa.resistcalc

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast

class DICEntrada2 : AppCompatActivity(), View.OnClickListener {

    private lateinit var txvValoresParaT : TextView
    private lateinit var edtxEntrada : EditText
    private lateinit var btAdicionar : Button
    private lateinit var btCalcular : Button
    private lateinit var txvValores : TextView

    private var _K : Int = intent.getIntExtra("DIC_K", 0)
    private var _N : Int = intent.getIntExtra("DIC_N", 0)
    private var _ALFA : Float = intent.getFloatExtra("DIC_ALFA", 0f)
    
    private val LISTA: MutableList<FloatArray> = mutableListOf()

    private var count : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_dic__entrada2)

        initViews()
    }

    private fun trataEntrada(entrada: String): FloatArray {

        val array = ArrayList<String>(entrada.trim().split(";", "\n").sorted())
        array.removeAll(listOf("", null))
        println(array)

        val amostras = FloatArray(array.count())
        for ((index, value) in array.withIndex()) {
            if (value.contains(","))
                amostras[index] = value.replace(",", ".").toFloat()
            else
                amostras[index] = value.toFloat()
        }

        return amostras
    }

    @SuppressLint("SetTextI18n")
    private fun addValues() {
        val tratamento = trataEntrada(edtxEntrada.text.toString())
        LISTA.add(tratamento)
        txvValores.text = txvValores.text.toString() + "\n\nT$count - $tratamento"

        edtxEntrada.text.clear()
        edtxEntrada.onEditorAction(EditorInfo.IME_ACTION_DONE)

//        TODO: seguir passos do doc

    }

    private fun calcular() {
        val DIC = DIC(_K, _N, _ALFA, LISTA)

        startActivity(
            Intent(this, DICResultado::class.java)
                .putExtra("DIC", DIC))
    }

    private fun initViews() {
        txvValoresParaT = findViewById(R.id.actDIC_txvValoresParaT)
        edtxEntrada = findViewById(R.id.actDIC_edtxEntrada)
        btAdicionar = findViewById(R.id.actDIC_btAdicionar)
        btCalcular = findViewById(R.id.actDIC_btCalcular)
        txvValores = findViewById(R.id.actDIC_txvValores)

        setListeners()
    }

    private fun setListeners() {
        btAdicionar.setOnClickListener(this)
        btAdicionar.setOnClickListener(this)
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        when (v) {
            btAdicionar -> {
                if (count < _K) {
                    count++
                    val entrada = trataEntrada(edtxEntrada.text.toString())
                    if (entrada.count() == _N) {
                        alert("Você digitou: $entrada.\n\nTodos os valores estão corretos?") {
                            title = "Só confirmando..."
                            positiveButton("Está correto") { addValues() }
                            negativeButton("Preciso corrigir") {}
                        }.show()
                    } else {
                        alert("Você digitou apenas ${entrada.count()} valores! Seu tratamento precisa ter exatamente $_N elementos.") {
                            title = "ERRO!"
                            positiveButton("CORRIGIR") {}
                        }.show()
                    }
                } else
                btAdicionar.isEnabled = false
            }

            btCalcular -> toast("CALCULAR")
        }
    }
}
