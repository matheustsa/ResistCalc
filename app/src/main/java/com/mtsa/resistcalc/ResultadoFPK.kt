package com.mtsa.resistcalc

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import java.text.DecimalFormat

class ResultadoFPK : AppCompatActivity(), View.OnClickListener {
    
    private lateinit var txvResultado: TextView
    private lateinit var btShare: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado_fpk)
    
        initViews()
        calcFPK()
    }
    
    
    private fun initViews() {
        txvResultado = findViewById(R.id.actResFPK_txvResultado)
        btShare = findViewById(R.id.actResFPK_btShare)
        btShare.setOnClickListener(this)
    }
    
    @SuppressLint("SetTextI18n")
    private fun calcFPK() {
        val amostras = intent.getFloatArrayExtra("Amostras")
//        val n = amostras.last()
        val gl = amostras.count()
//        val alpha = 0.2     //valor fixo
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
        // Passo 1 - Calcular a Média
        val media = calculaMedia(amostras)
        // Passo 2 - Calcular a da diferença entre as resistências e a média (n-1), elevando ao quadrado cada elemento
        val variancia = calculaVariancia(amostras, media)
        // Passo 3 - Extrair a raiz da média das variâncias
        val desvio = Math.sqrt(variancia)
        
        // Calcula t_crítico
        val t_critico = t_student[gl - 1]
        
        // Calcula fpk,est (media das peças - valor da tabela * desvio)
        val fpk = media - (t_critico * desvio)
    
        txvResultado.text = "Amostras: ${amostras.toList()}" +
                "\n\nQuantidade: ${amostras.size} elementos" +
                "\nMédia das amostras: " + String.format("%.2f", media) +
                "\n\nDesvio padrão: " + String.format("%.2f", desvio) +
                "\nt_crítico: $t_critico" +
                "\n\nfpk,est: " + String.format("%.2f", fpk) + " MPa"
    }
    
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.actResFPK_btShare -> {
                val shareMsg = "-[ ResistCalc App ]-\n\n" +
                        "Os resultados obtidos foram:\n\n" +
                        txvResultado.text.toString() +
                        "\n\nDownload via: * GooglePlay link *"
                
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, shareMsg)
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(sendIntent, "Enviar para..."))
            }
        }
    }
    
    private fun calculaMedia(lista: FloatArray): Double {
        var media = 0.0
        for (i in lista.indices)
            media += lista[i]
        media /= lista.count()
        
        return media
    }
    
    private fun calculaVariancia(amostras: FloatArray, media: Double): Double {
        var variancia = 0.0
        for (i in amostras.indices)
            variancia += Math.pow((amostras[i] - media), 2.0).toFloat()
        variancia /= amostras.count() - 1
        
        return variancia
    }
    
    private fun twoDecimals(number: Float): Float {
        val aux = DecimalFormat("#.##")
        return aux.format(number).toFloat()
    }
    
}
