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
        // recebe os valores da classe de entrada de dados
        val amostras = intent.getFloatArrayExtra("Amostras")
        // armazena a quantidade de amostras em “n”
        val n = amostras.count()
        // determina o valor de “i” dependendo da quantidade de amostras
        // n/2, se for par;
        // (n-1)/2, se for ímpar;
        var i = if (n%2 == 0) n/2 else (n-1)/2
    
        // como todos os valores utilizam o i-1, é mais prático já decremenatr agora
        i--
    
        // cria uma sublista do menor elemento da amostra, até a posição de "i" e soma todos esses valores
        val soma = amostras.toList().subList(0,i).sum()
        // "fbi" será o elemento na posição "i"
        val fbi = amostras[i]
    
        // nesta linha há o emprego da equação para obtenção da resistência à compressão estimada em blocos cerâmicos
        val fbk = ((2*soma)/i) - fbi
    
        // o "fbm" é a média da resistência à compressão de todos os corpos-de-prova da amostra
        val fbm = amostras.sum()/amostras.count()
    
        // checa os valores de Ø em função da quantidade de blocos da amostra
        val tabela = arrayOf(0.89F, 0.89F, 0.89F, 0.89F, 0.89F, 0.89F, 0.91F, 0.93F, 0.94F, 0.96F, 0.97F, 0.98F, 0.99F, 1F, 1.01F, 1.02F, 1.02F, 1.04F)
        val menorLimite = if (n < 18)
            amostras[0] * tabela[n - 1]
        else
            amostras[0] * tabela.lastIndex
    
        // verifica os requisítos para finalmente obter a resistência característica do lote (fbk)
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
    
    private fun shareResults() {
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
    
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.actResFBK_btShare -> shareResults()
        }
    }
}
