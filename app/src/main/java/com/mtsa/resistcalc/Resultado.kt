package com.mtsa.resistcalc

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.act_resultado.*
import kotlin.math.pow
import kotlin.math.sqrt

class Resultado : AppCompatActivity(), View.OnClickListener {
    
    private lateinit var txvResultado: TextView
    private lateinit var AMOSTRAS: FloatArray
    private var MEDIA: Float = 0f
    private var TCRITICO = 0f
    private var VARIANCIA = 0f
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_resultado)
        
        initViews()
    
        AMOSTRAS = intent.getFloatArrayExtra("AMOSTRAS")
        
        when (intent.getStringExtra("OP")) {
            "FBK" -> {
                actRes_btShare.setBackgroundResource(R.drawable.fbk_share)
                getFBK(AMOSTRAS)
            }
            "FPK" -> {
                actRes_btShare.setBackgroundResource(R.drawable.fpk_share)
                getFPK(AMOSTRAS)
            }
        }
    }
    
    private fun initViews() {
        txvResultado = findViewById(R.id.actRes_txvResultado)
        actRes_btShare.setOnClickListener(this)
        actRes_btDetalhar.setOnClickListener(this)
    }
    
    @SuppressLint("SetTextI18n")
    private fun getFBK(amostras: FloatArray) {
        // armazena a quantidade de amostras em “n”
        val n = amostras.count()
        // determina o valor de “i” dependendo da quantidade de amostras
        // n/2, se for par;
        // (n-1)/2, se for ímpar;
        var i = if (n % 2 == 0) n / 2 else (n - 1) / 2
        
        // como todos os valores utilizam o i-1, é mais prático já decremenatr agora
        i--
        
        // cria uma sublista do menor elemento da amostra, até a posição de "i" e soma todos esses valores
        val soma = amostras.toList().subList(0, i).sum()
        // "fbi" será o elemento na posição "i"
        val fbi = amostras[i]
        
        // nesta linha há o emprego da equação para obtenção da resistência à compressão estimada em blocos cerâmicos
        val fbk = ((2 * soma) / i) - fbi
        
        // o "fbm" é a média da resistência à compressão de todos os corpos-de-prova da amostra
        val fbm = amostras.sum() / amostras.count()
        
        // checa os valores de Ø em função da quantidade de blocos da amostra
        val tabela = arrayOf(
            0.89F,
            0.89F,
            0.89F,
            0.89F,
            0.89F,
            0.89F,
            0.91F,
            0.93F,
            0.94F,
            0.96F,
            0.97F,
            0.98F,
            0.99F,
            1F,
            1.01F,
            1.02F,
            1.02F,
            1.04F
        )
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
    
    @SuppressLint("SetTextI18n")
    private fun getFPK(amostras: FloatArray) {
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
        val media = (amostras.sum() / amostras.size).toDouble()
        MEDIA = media.toFloat()
        
        // Passo 2 - Calcular a diferença entre as resistências e a média (n-1), elevando ao quadrado cada elemento
        val variancia = calculaVariancia(amostras, media)
        VARIANCIA = variancia.toFloat()
        
        // Passo 3 - Extrair a raiz da média das variâncias
        val desvio = sqrt(variancia)
        
        // Calcula t_crítico
        val t_critico = t_student[gl - 1]
        TCRITICO = t_critico.toFloat()
        
        // Calcula fpk,est (media das peças - (valor da tabela * desvio))
        val fpk = media - (t_critico * desvio)
        
        txvResultado.text = "Amostras: ${amostras.toList()}" +
                "\n\nQuantidade: ${amostras.size} elementos" +
                "\nMédia das amostras: " + String.format("%.2f", media) +
                "\n\nDesvio padrão: " + String.format("%.2f", desvio) +
                "\nt_crítico: $t_critico" +
                "\n\nfpk,est: " + String.format("%.2f", fpk) + " MPa"
    }
    
    private fun calculaVariancia(amostras: FloatArray, media: Double): Double {
        var variancia = 0.0
        for (i in amostras.indices)
            variancia += (amostras[i] - media).pow(2.0).toFloat()
        variancia /= amostras.count() - 1
        
        return variancia
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
    
    private fun detalharResultados(amostras: FloatArray) {
        
        when (intent.getStringExtra("OP")) {
            "FBK" -> startActivity(
                Intent(this, DetalharFBK::class.java).putExtra(
                    "AMOSTRAS",
                    amostras
                )
            )
            "FPK" -> startActivity(
                Intent(this, DetalharFPK::class.java).putExtra("AMOSTRAS", amostras)
                    .putExtra("MEDIA", MEDIA)
                    .putExtra("TCRITICO", TCRITICO)
                    .putExtra("VARIANCIA", VARIANCIA)
            )
        }
    }
    
    override fun onClick(v: View?) {
        when (v) {
            actRes_btShare -> shareResults()
            actRes_btDetalhar -> detalharResultados(AMOSTRAS)
        }
    }
}
