package com.mtsa.resistcalc

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import java.text.DecimalFormat

class FPK : AppCompatActivity() {

    private lateinit var txvResultadoFPK: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fpk)

        initViews()
//        test()
        calcFPK()
    }


    private fun initViews() {
        txvResultadoFPK = findViewById(R.id.actResFPK_txvResultado)
    }

    @SuppressLint("SetTextI18n")
    private fun calcFPK() {
        val resistencias = floatArrayOf(12.4F, 12.6F, 12.4F, 13.5F, 15.4F, 16.4F, 17.4F).sorted()
        val n = resistencias.last()
        val gl = resistencias.count()
        val alpha = 0.2     //valor fixo

        // Calcular Desvio Padrão
        // Passo 1 - Calcula a Média
        val media = calculaMedia(resistencias).toFloat()
        println(media)
        // Passo 2 - Calcula a diferença entre as resistências e a média, elevado ao quadrado cada elemento
        val diferenca = FloatArray(gl)
        for (i in resistencias.indices)
            diferenca[i] = round(Math.pow((resistencias[i] - media).toDouble(), 2.0).toFloat())
        // Passo 3 - Calcula a média das diferenças e extrai a raiz
        var desvio = Math.sqrt(calculaMedia(diferenca.toList()))
//        desvio = 2.08087F.toDouble()
        //TODO arrumar desvio

        // Calcula t_crítico
        val t_critico = calcInversa(alpha.toInt(), gl) // deve ser sempre positivo (absoluto / módulo)
        //TODO arrumar t_critico

        // Calcula fpk,est
        val fpk = media - (t_critico * desvio)


        // FPK = media das peças - valor da tabela * desvio
        val fpk2 = media - (0.89603 * desvio)


        txvResultadoFPK.text = "Amostras: $resistencias" +
                "\n\nMédia: $media" +
                "\nDiferença: ${diferenca.toList()}"  +
                "\nDesvio: " + String.format("%.2f", desvio) +
                "\n\nt_critico: $t_critico" +
                "\n\nfpk,est: " + String.format("%.2f", fpk2)
    }

    private fun calculaMedia(lista: List<Float>): Double {
        var media = 0.0
        for (i in lista.indices)
            media += lista[i]
        media /= lista.count()

        return media
    }

    private fun calcInversa(prob: Int, liberdade: Int): Int {
        val inversa = prob % liberdade
        for (i in 0 until liberdade)
            if ((inversa * i) % liberdade == 1)
                return i
        return 1
    }
    
    private fun round(number: Float): Float {
        val aux = DecimalFormat("#.##")
        return aux.format(number).toFloat()
    }
    
    
}
