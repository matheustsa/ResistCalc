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

    private val txvValoresParaT : TextView by lazy { findViewById<TextView>(R.id.actDIC_txvValoresParaT) }
    private val edtxEntrada : EditText by lazy { findViewById<EditText>(R.id.actDIC_edtxEntrada) }
    private val btAdicionar : Button by lazy { findViewById<Button>(R.id.actDIC_btAdicionar) }
    private val btCalcular : Button by lazy { findViewById<Button>(R.id.actDIC_btCalcular) }
    private val txvValores : TextView by lazy { findViewById<TextView>(R.id.actDIC_txvValores) }

    private var k : Int = 0
    private var n : Int = 0
    private var alfa : Float = 0f
    
    private val lista: MutableList<List<Double>> = mutableListOf()

    private var count : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_dic__entrada2)

        initViews()
    }

    override fun onClick(v: View?) {
        when (v) {
            btAdicionar -> {
                val entrada = getValues(edtxEntrada.text.toString())
                if (entrada.count() == n) {
                    alert("Você digitou: ${entrada}.\n\nTodos os valores estão corretos?") {
                        title = "Só confirmando..."
                        positiveButton("Está correto") { addValues(entrada) }
                        negativeButton("Preciso corrigir") {}
                    }.show()
                } else {
                    alert("Você digitou ${entrada.count()} valores! Seu tratamento precisa ter exatamente $n elementos.") {
                        title = "ERRO!"
                        positiveButton("CORRIGIR") {}
                    }.show()
                }
            }

            btCalcular -> calcular()
        }
    }

    private fun getValues(entrada: String): List<Double> {

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
        lista.add(entrada)
        if (count == 1)
            txvValores.text = "T${count} - $entrada"
        else
            txvValores.text = txvValores.text.toString() + "\nT${count} - $entrada"

        edtxEntrada.text.clear()
        edtxEntrada.onEditorAction(EditorInfo.IME_ACTION_DONE)

        count++
        println(count)

        if (count > k) {
            btAdicionar.isEnabled = false
            btAdicionar.visibility = View.GONE
            btCalcular.visibility = View.VISIBLE
            txvValoresParaT.text = "Tratamentos"
            edtxEntrada.isEnabled = false
            edtxEntrada.hint = "Amostras registradas com sucesso..\n\nClique em CALCULAR para prosseguir"
        }
        else {
            txvValoresParaT.text = "Valores para T${count}"
        }

    }

    private fun calcular() {
        val dic = DIC2(k, n, alfa, lista.toList())

        println("k: ${dic.k}  -  n: ${dic.n}  -  alfa: ${dic.alfa}\n"
            + "${dic.lista}")

        startActivity(
            Intent(this, DICResultado2::class.java)
                .putExtra("DIC", dic))
    }

    private fun initViews() {
        k = intent.getIntExtra("DIC_K", 0)
        n = intent.getIntExtra("DIC_N", 0)
        alfa = intent.getFloatExtra("DIC_ALFA", 0f)

        btAdicionar.setOnClickListener(this)
        btCalcular.setOnClickListener(this)
    }
}
