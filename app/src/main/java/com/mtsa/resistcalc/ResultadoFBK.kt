package com.mtsa.resistcalc

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView


class ResultadoFBK : AppCompatActivity() {

    private lateinit var txvResultado: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado_fbk)

        initViews()
        getValues()
    }

    private fun initViews() {
        txvResultado = findViewById(R.id.actResFBK_txvResultado)
    }

    @SuppressLint("SetTextI18n")
    private fun getValues() {
        val amostras = intent.getFloatArrayExtra("Amostras")
        val n = amostras.count()
        var i = if (n%2 == 0) n/2 else (n-1)/2

        //tudo é com i-1, então pra facilitar já decrementa logo
        i--

        val soma = amostras.toList().subList(0,i).sum()
        val fbi = amostras[i]
        val fbk = ((2*soma)/i) - fbi

        val fbm = amostras.sum()/amostras.count()

        val tabela = arrayOf(0.89F, 0.89F, 0.89F, 0.89F, 0.89F, 0.89F, 0.91F, 0.93F, 0.94F, 0.96F, 0.97F, 0.98F, 0.99F, 1F, 1.01F, 1.02F, 1.02F, 1.04F)
        val menorLimite = amostras[0]*tabela[n-1]
        //TODO tratar n>18
    
        val resistencia: Float
        if (fbk >= fbm)
            resistencia = fbm
        else if (fbk < menorLimite)
            resistencia = menorLimite
        else //menorLimite < FBK < fbm
            resistencia = fbk



        txvResultado.text = "Amostras: ${amostras.toList()}" +
                "\n\nn = $n" +
                "\ni = ${i+1}" +
                "\n\nfbk,est = " + String.format("%.2f", fbk) +
                "\nfbm = " + String.format("%.2f", fbm) +
                "\n\nresistência do lote = " + String.format("%.2f", resistencia)
    }
}
