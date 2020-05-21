package com.mtsa.resistcalc

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.mtsa.utils.Utils
import com.mtsa.utils.Utils.roundDec

class ResultadoFPK : AppCompatActivity(), View.OnClickListener {

    private val txvAmostras: TextView by lazy { findViewById<TextView>(R.id.actResFPK2_txvAmostras) }
    private val txvMediaDasAmostras: TextView by lazy { findViewById<TextView>(R.id.actResFPK2_txvMediaAmostras) }
    private val txvDesvioPadrao: TextView by lazy { findViewById<TextView>(R.id.actResFPK2_txvDesvioPadrao) }
    private val txvTCritico: TextView by lazy { findViewById<TextView>(R.id.actResFPK2_txvTCritico) }
    private val txvFPK: TextView by lazy { findViewById<TextView>(R.id.actResFPK2_txvFPK) }

    private val btShare: ImageButton by lazy { findViewById<ImageButton>(R.id.actResFPK2_btShare) }
    private val btDetalhar: Button by lazy { findViewById<Button>(R.id.actResFPK2_btDetalhar) }

    private lateinit var AMOSTRAS: FloatArray
    private var MEDIA: Float = 0f
    private var TCRITICO = 0f
    private var VARIANCIA = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_resultado_fpk)

        setListeners()

        AMOSTRAS = intent.getFloatArrayExtra("AMOSTRAS")
        getFPK(AMOSTRAS)

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
        val media = amostras.average()
        MEDIA = media.toFloat()

        // Passo 2 - Calcular a diferença entre as resistências e a média (n-1), elevando ao quadrado cada elemento
        val variancia = Utils.variancia(amostras)
        VARIANCIA = variancia.toFloat()

        // Passo 3 - Extrair a raiz da média das variâncias
        val desvio = Utils.desvioPadrao(amostras)

        // Calcula t_crítico
        val t_critico = t_student[gl - 1]
        TCRITICO = t_critico.toFloat()

        // Calcula fpk,est (media das peças - (valor da tabela * desvio))
        val fpk = media - (t_critico * desvio)

        var sAmostras = ""
        amostras.forEach { sAmostras += it.toString() + "\t\t" }
        txvAmostras.text = sAmostras +
                "\n\nQuantidade: ${amostras.size} elementos"

        txvMediaDasAmostras.text = roundDec(media, 2).toString()
        txvDesvioPadrao.text = desvio.toString()
        txvTCritico.text = t_critico.toString()
        txvFPK.text  = roundDec(fpk, 2).toString()

    }

    private fun shareResults() {
        val shareMsg = "-[ ResistCalc App ]-\n\n" +
                "Os resultados obtidos foram:\n\n" +
                txvAmostras.text.toString() +
                "\n\nDownload via: * GooglePlay link *"

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareMsg)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(sendIntent, "Enviar para..."))
    }

    private fun detalharResultados(amostras: FloatArray) {

        startActivity(
            Intent(this, DetalharFPK::class.java).putExtra("AMOSTRAS", amostras)
                .putExtra("MEDIA", MEDIA)
                .putExtra("TCRITICO", TCRITICO)
                .putExtra("VARIANCIA", VARIANCIA)
        )
    }

    private fun setListeners() {
        btDetalhar.setOnClickListener(this)
        btShare.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            btShare -> shareResults()
            btDetalhar -> detalharResultados(AMOSTRAS)
        }
    }
}
