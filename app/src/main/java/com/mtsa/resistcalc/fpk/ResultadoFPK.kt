package com.mtsa.resistcalc.fpk

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.mtsa.resistcalc.R
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

    private lateinit var FPK: FPK
    private lateinit var AMOSTRAS: FloatArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_resultado_fpk)


        AMOSTRAS = intent.getFloatArrayExtra("AMOSTRAS")
        FPK = FPK(AMOSTRAS)

        setListeners()
        setViews()
    }

    private fun setListeners() {
        btDetalhar.setOnClickListener(this)
        btShare.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    private fun setViews(){
        txvAmostras.text = "${FPK.sLista}\n\nQuantidade: ${FPK.amostras.size} elementos"
        txvMediaDasAmostras.text = FPK.sMedia
        txvDesvioPadrao.text = FPK.sDesvio
        txvTCritico.text = FPK.sTcrit
        txvFPK.text = FPK.sFPK
    }

    private fun shareResults() {
        val shareMsg = "-[ ResistCalc App ]-\n\n" +
                "Os resultados obtidos foram:\n\n" +
                txvAmostras.text.toString() +
                "\n\nMédia das amostras: ${txvMediaDasAmostras.text}" +
                "Desvio padrão: ${txvDesvioPadrao.text}" +
                "T Crítico: ${txvTCritico.text}" +
                "fpk,est: ${txvFPK.text} MPa" +
                "\n\nDownload via: \n" +
                "https://play.google.com/store/apps/details?id=com.mtsa.resistcalc"


        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareMsg)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(sendIntent, "Enviar para..."))
    }

    private fun detalharResultados() {
        startActivity(
            Intent(this, DetalharFPK::class.java)
                .putExtra("AMOSTRAS", AMOSTRAS)
                .putExtra("MEDIA", FPK.fMedia)
                .putExtra("TCRITICO", FPK.fTcrit)
                .putExtra("VARIANCIA", FPK.fVariancia)
        )
    }



    override fun onClick(v: View?) {
        when (v) {
            btShare -> shareResults()
            btDetalhar -> detalharResultados()
        }
    }
}
