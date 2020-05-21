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

class DICEntrada2 : AppCompatActivity(), View.OnClickListener {

    private lateinit var txvValoresParaT : TextView
    private lateinit var edtxEntrada : EditText
    private lateinit var btAdicionar : Button
    private lateinit var btCalcular : Button
    private lateinit var txvValores : TextView

    private var _K : Int = 0
    private var _N : Int = 0
    private var _ALFA : Float = 0f
    
    private val LISTA: MutableList<FloatArray> = mutableListOf()
    private val LISTA2: MutableList<List<Double>> = mutableListOf()

    private var count : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_dic__entrada2)

        initViews()
    }

    override fun onClick(v: View?) {
        when (v) {
            btAdicionar -> {
                val entrada = trataEntrada2(edtxEntrada.text.toString())
                if (entrada.count() == _N) {
                    alert("Você digitou: ${entrada}.\n\nTodos os valores estão corretos?") {
                        title = "Só confirmando..."
                        positiveButton("Está correto") { addValues(entrada) }
                        negativeButton("Preciso corrigir") {}
                    }.show()
                } else {
                    alert("Você digitou ${entrada.count()} valores! Seu tratamento precisa ter exatamente $_N elementos.") {
                        title = "ERRO!"
                        positiveButton("CORRIGIR") {}
                    }.show()
                }
            }

            btCalcular -> calcular()
        }
    }

    private fun trataEntrada2(entrada: String): List<Double> {

       val arrayList = ArrayList<String> (entrada.trim().split(";", "\n").sorted())
        arrayList.removeAll(listOf("", null))
        println(arrayList)

        val subList = mutableListOf<Double>()
        for ((index, value) in arrayList.withIndex()) {
            if (value.contains(","))
                subList.add(value.replace(",", ".").toDouble())
            else
                subList.add(value.toDouble())
        }

        return subList.toList()
    }

    @SuppressLint("SetTextI18n")
    private fun addValues(entrada: List<Double>) {
        LISTA2.add(entrada)
        txvValores.text = txvValores.text.toString() + "\n\nT${count} - $entrada"
        edtxEntrada.text.clear()
        edtxEntrada.onEditorAction(EditorInfo.IME_ACTION_DONE)

        count++

        if (count > _K)
            btAdicionar.isEnabled = false
        else
            txvValoresParaT.text = "Valores para T${count}"

    }

    private fun calcular() {
        val DIC2 = DIC2(_K, _N, _ALFA, LISTA2.toList())

        println("k: ${DIC2.k}  -  n: ${DIC2.n}  -  alfa: ${DIC2.alfa}\n"
            + "${DIC2.lista}")

        startActivity(
            Intent(this, DICResultado::class.java)
                .putExtra("DIC", DIC2))
    }

    private fun initViews() {
        txvValoresParaT = findViewById(R.id.actDIC_txvValoresParaT)
        edtxEntrada = findViewById(R.id.actDIC_edtxEntrada)
        btAdicionar = findViewById(R.id.actDIC_btAdicionar)
        btCalcular = findViewById(R.id.actDIC_btCalcular)
        txvValores = findViewById(R.id.actDIC_txvValores)

        _K = intent.getIntExtra("DIC_K", 0)
        _N = intent.getIntExtra("DIC_N", 0)
        _ALFA = intent.getFloatExtra("DIC_ALFA", 0f)

        setListeners()
    }



    private fun setListeners() {
        btAdicionar.setOnClickListener(this)
        btCalcular.setOnClickListener(this)
    }
}
