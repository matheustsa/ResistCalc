package com.mtsa.resistcalc

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageButton
import android.widget.TextView


class ResultadoFBK : AppCompatActivity(), View.OnClickListener {
    
    private lateinit var txvResultado: TextView
    private lateinit var btShare: ImageButton

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado_fbk)

        initViews()
        getValues()
    }

    private fun initViews() {
        txvResultado = findViewById(R.id.actResFBK_txvResultado)
        btShare = findViewById(R.id.actResFBK_btShare)
        btShare.setOnClickListener(this)
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
//        val menorLimite = amostras[0]*tabela[n-1]
        val menorLimite = if (n < 18)
            amostras[0] * tabela[n - 1]
        else
            amostras[0] * tabela.lastIndex
    
        val resistencia = when {
            fbk >= fbm -> fbm
            fbk < menorLimite -> menorLimite
            else -> fbk
        }



        txvResultado.text = "Amostras: ${amostras.toList()}" +
                "\n\nQuantidade: $n elementos" +
                "\n\nfbk,est = " + String.format("%.2f", fbk) + " MPa" +
                "\nfbm = " + String.format("%.2f", fbm) + " MPa (resistência média)" +
                "\n\nResistência do lote = " + String.format("%.2f", resistencia) + " MPa"
    }
    
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.actResFBK_btShare -> {
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
}
