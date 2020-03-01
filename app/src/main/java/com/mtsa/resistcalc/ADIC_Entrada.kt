package com.mtsa.resistcalc

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mtsa.utils.Utils
import kotlinx.android.synthetic.main.act_adic__entrada.*


/*
     --------------------------------------------------------------------------
     ANÁLISE DE VARIÂNCIA (ANOVA) - DELINEAMENTO INTEIRAMENTE CASUALIZADO (DIC)
     --------------------------------------------------------------------------
*/
class ADIC_Entrada : AppCompatActivity(), View.OnClickListener {

    private lateinit var txvAmostras: TextView
    private var amostras: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_adic__entrada)

        txvAmostras = findViewById(R.id.actADIC_txvAmostras)
        txvAmostras.text = ""

        setListeners()
    }

    private fun setListeners() {
        actADIC_btAddAmostras.setOnClickListener(this)
        actADIC_btCalcular.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    private fun addString() {

        if (!actADIC_edtxAmostras.text.isNullOrBlank()) {
            val string = actADIC_edtxAmostras.text.trim()
            actADIC_txvAmostras.text = actADIC_txvAmostras.text.toString() + "\n" + string

            actADIC_edtxAmostras.text.clear()
            actADIC_edtxAmostras.onEditorAction(EditorInfo.IME_ACTION_DONE)

            amostras = "$amostras $string"
            println("------------------------------ $amostras")
        }
    }

    private fun calcular(amostras: String) {
        val result = amostras.trim().split(" ").map { it.toFloat() }
        actADIC_txvAmostras.text = result.toString()

    }

    override fun onClick(v: View?) {
        when (v) {
            actADIC_btAddAmostras -> addString()
            actADIC_btCalcular -> calcular(amostras)
//            actADIC_btCalcular -> toast("calcular")
        }
    }

    /*

    TODO: média das amostras
    TODO: desvio padrão
    TODO: variância
    TODO: coeficiente de variação (desvio padrão/média) * 100
    TODO: média global

     */


}
