package com.mtsa.resistcalc.fbk

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.mtsa.resistcalc.R
import com.mtsa.utils.Utils.roundDec

class ResultadoFBK : AppCompatActivity(), View.OnClickListener {

    private val txvAmostras: TextView by lazy { findViewById<TextView>(R.id.actResFBK_txvAmostras) }
    private val txvFBK: TextView by lazy { findViewById<TextView>(R.id.actResFBK_txvFBK) }
    private val txvFBM: TextView by lazy { findViewById<TextView>(R.id.actResFBK_txvFBM) }
    private val txvResistLote: TextView by lazy { findViewById<TextView>(R.id.actResFBK_txvResistenciaLote) }
    private val btShare: ImageButton by lazy { findViewById<ImageButton>(R.id.actResFBK_btShare) }
    private val btDetalhar: Button by lazy { findViewById<Button>(R.id.actResFBK_btDetalhar) }

    private lateinit var AMOSTRAS: FloatArray
    private lateinit var FBK_CLASSE: FBK


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_resultado_fbk)

        AMOSTRAS = intent.getFloatArrayExtra("AMOSTRAS")
        getFBK(AMOSTRAS)
        FBK_CLASSE = FBK(AMOSTRAS)

        btShare.setOnClickListener(this)
        btDetalhar.setOnClickListener(this)
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

        var sAmostras = ""
        amostras.forEach { sAmostras += it.toString() + "\t\t" }
        txvAmostras.text = sAmostras +
                "\n\nQuantidade: $n elementos"


//        txvFBM.text = roundDec(fbm.toDouble(),2).toString()
//        txvFBK.text = roundDec(fbk.toDouble(), 2).toString()
        txvFBK.text = FBK_CLASSE.sFbk
        txvFBM.text = roundDec(fbm.toDouble(),2).toString()
        txvResistLote.text = resistencia.toString()
    }

    private fun shareResults() {
        val shareMsg = "-[ ResistCalc App ]-\n\n" +
                "Os resultados obtidos foram:\n\n" +
                txvAmostras.text.toString() +
                "\n\nDownload via: \n" +
                "https://play.google.com/store/apps/details?id=com.mtsa.resistcalc"

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareMsg)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(sendIntent, "Enviar para..."))
    }

    private fun detalharResultados(amostras: FloatArray) {
        startActivity(
            Intent(this, DetalharFBK::class.java)
                .putExtra("AMOSTRAS", amostras)
                .putExtra("FBK", FBK_CLASSE))
    }

    override fun onClick(v: View?) {
        when (v) {
            btDetalhar -> detalharResultados(AMOSTRAS)
            btShare -> shareResults()
        }
    }

}
