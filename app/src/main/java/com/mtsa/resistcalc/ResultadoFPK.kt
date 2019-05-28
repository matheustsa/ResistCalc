package com.mtsa.resistcalc

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import java.text.DecimalFormat

class ResultadoFPK : AppCompatActivity() {
    
    private lateinit var txvResultadoFPK: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado_fpk)
    
        initViews()
        calcFPK()
    }
    
    
    private fun initViews() {
        txvResultadoFPK = findViewById(R.id.actResFPK_txvResultado)
    }
    
    @SuppressLint("SetTextI18n")
    private fun calcFPK() {
        val resistencias = floatArrayOf(12.4F, 12.4F, 12.6F, 13.5F, 15.4F, 16.4F, 17.4F).sorted()
        val n = resistencias.last()
        val gl = resistencias.count()
        val alpha = 0.2     //valor fixo
        val t_student = doubleArrayOf(
            1.3764,
            1.0607,
            0.9785,
            0.9410,
            0.9195,
            0.9057,
            0.8960,
            0.8889,
            0.8834,
            0.8791,
            0.8755,
            0.8726,
            0.8702,
            0.8681,
            0.8662,
            0.8647,
            0.8633,
            0.8620,
            0.8610,
            0.8600,
            0.8591,
            0.8583,
            0.8575,
            0.8569,
            0.8562,
            0.8557,
            0.8551,
            0.8546,
            0.8542,
            0.8538
        )
        
        // Calcular Desvio Padrão
        // Passo 1 - Calcula a Média
        val media = calculaMedia(resistencias).toFloat()
        // Passo 2 - Calcula a diferença entre as resistências e a média (n-1), elevado ao quadrado cada elemento
//        val variancia = FloatArray(gl)
        var variancia2 = 0.0
        for (i in resistencias.indices)
//            variancia[i] = twoDecimals(Math.pow((resistencias[i] - media).toDouble(), 2.0).toFloat())
            variancia2 += Math.pow((resistencias[i] - media).toDouble(), 2.0).toFloat()
        variancia2 /= resistencias.count() - 1
        // Passo 3 - Calcula a média das diferenças e extrai a raiz
//        var desvio = Math.sqrt(calculaMediaVariancia(variancia.toList()))
        val desvio = Math.sqrt(variancia2)
        
        // Calcula t_crítico
        val t_critico = t_student[gl - 1]
        
        // Calcula fpk,est (media das peças - valor da tabela * desvio)
        val fpk = media - (t_critico * desvio)

//        txvResultadoFPK.text = "Amostras: $resistencias" +
//                "\n\nMédia das amostras: $media" +
//                "\n\nVariâncias: ${variancia.toList()}"  +
//                "\n\nVariância total: ${variancia.sum()}" +
//                "\nVariância média: ${twoDecimals(calculaMedia(variancia.toList()).toFloat())}" +
//                "\n\nDesvio padrão: " + String.format("%.2f", desvio) +
//                "\nt_critico: $t_critico" +
//                "\n\nfpk,est: " + String.format("%.2f", fpk)
        
        txvResultadoFPK.text = "Amostras: $resistencias" +
                "\n\nMédia das amostras: $media" +
                "\n\nDesvio padrão: " + String.format("%.2f", desvio) +
                "\nt_critico: $t_critico" +
                "\n\nfpk,est: " + String.format("%.2f", fpk)
    }
    
    private fun calculaMedia(lista: List<Float>): Double {
        var media = 0.0
        for (i in lista.indices)
            media += lista[i]
        media /= lista.count()
        
        return media
    }
    
    private fun calculaMediaVariancia(lista: List<Float>): Double {
        var media = 0.0
        for (i in lista.indices)
            media += lista[i]
        
        // PORQUE  ESSA MERDA PRECISA SER COM N-1 EU NÃO SEI, MAS FUNCIONOU ASSIM ENTÃO FODA-SE
        media /= lista.count() - 1
        
        return media
    }
    
    private fun twoDecimals(number: Float): Float {
        val aux = DecimalFormat("#.##")
        return aux.format(number).toFloat()
    }
    
}
