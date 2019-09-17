package com.mtsa.resistcalc

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import org.jetbrains.anko.alert
import java.text.DecimalFormat
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class ResultadoFPK : AppCompatActivity(), View.OnClickListener {
    
    private lateinit var txvResultado: TextView
    private lateinit var btShare: ImageButton
    private lateinit var btDetalhar: Button
    private lateinit var msgDetalhar: String

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
        btDetalhar = findViewById(R.id.actResFPK_btDetalhar)
        btDetalhar.setOnClickListener(this)
        
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
    
        // Passo 2 - Calcular a diferença entre as resistências e a média (n-1), elevando ao quadrado cada elemento
        val variancia = calculaVariancia(amostras, media)
    
        // Passo 3 - Extrair a raiz da média das variâncias
        val desvio = sqrt(variancia)
        
        // Calcula t_crítico
        val t_critico = t_student[gl - 1]
    
        // Calcula fpk,est (media das peças - (valor da tabela * desvio))
        val fpk = media - (t_critico * desvio)
    
        msgDetalhar = detalharCalculos(amostras, media, variancia, desvio, t_critico, fpk)
    
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
    
            R.id.actResFPK_btDetalhar -> {
                alert(msgDetalhar, "Como calcular o FPK,EST").show()
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
            variancia += (amostras[i] - media).pow(2.0).toFloat()
        variancia /= amostras.count() - 1
        
        return variancia
    }
    
    private fun twoDecimals(number: Double): Float {
        val aux = DecimalFormat("#.##")
        return aux.format(number).toFloat()
    }
    private fun twoDecimals(number: Float): Float {
        val aux = DecimalFormat("#.##")
        return aux.format(number).toFloat()
    }
    
    private fun detalharCalculos(
        amostras: FloatArray,
        media: Double,
        variancia: Double,
        desvio: Double,
        tCritico: Double,
        fpk: Double
    ): String {
        
        val media2 = twoDecimals(media)
        val variancia2 = twoDecimals(variancia)
        val desvio2 = twoDecimals(desvio)
        val fpk2 = twoDecimals(fpk)
        
        val m1 = twoDecimals(amostras[1] / amostras.size)
        val m2 = twoDecimals(amostras[2] / amostras.size)
        val m3 = twoDecimals(amostras[3] / amostras.size)
        
        val v1 = twoDecimals(sqrt(abs(amostras[1] - media)))
        val v2 = twoDecimals(sqrt(abs(amostras[2] - media)))
        val v3 = twoDecimals(sqrt(abs(amostras[3] - media)))
        
        return "Passo 1 - Calcular a média total das amostras: \n\n" +
                "   media = amostra / quant elementos \n" +
                "   média 1  =  ${amostras[1]} / ${amostras.size}  =  $m1 \n" +
                "   média 2  =  ${amostras[2]} / ${amostras.size}  =  $m2 \n" +
                "   média 3  =  ${amostras[3]} / ${amostras.size}  =  $m3 \n" +
                "   ... \n" +
                "   media total = $m1  +  $m2  +  $m3  +  ... \n\n" +
                "-> média total: $media2 \n\n\n" +
                
                "Passo 2 - Calcular a variância total (diferença entre as resistências e a média, elevando ao quadrado cada elemento):\n\n" +
                "   variancia  =  ²√ (amostra - media) \n" +
                "   v1  =  ²√ (${amostras[1]} - $media2)  =  $v1 \n" +
                "   v2  =  ²√ (${amostras[2]} - $media2)  =  $v2 \n" +
                "   v3  =  ²√ (${amostras[3]} - $media2)  =  $v3 \n" +
                "   ...\n" +
                "   variância total = $v1  +  $v2  +  $v3  +  ... \n\n" +
                "-> variância total: $variancia2 \n\n\n" +
                
                "Passo 3 - Adquirir o desvio padrão (raiz quadrada da variância total) \n\n" +
                "   desvio padrão  =  ²√ variância total\n\n" +
                "-> desvio padrão: $desvio2 \n\n\n" +
                
                "Passo 4: Buscar o valor correspondente na tabela de tStudent \n\n" +
                "-> t crítico: $tCritico \n\n\n" +
                
                "Passo 5: Calcular fpk,est \n\n" +
                "   fpk,est  =  média - (t crítico  X  desvio) \n" +
                "   fpk,est = $media2 - ($tCritico  X  $desvio2) \n\n" +
                "-> fpk,est: $fpk2}"
    }
    
}
