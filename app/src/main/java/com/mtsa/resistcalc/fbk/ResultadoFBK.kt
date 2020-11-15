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

class ResultadoFBK : AppCompatActivity(), View.OnClickListener {

    private val txvAmostras: TextView by lazy { findViewById<TextView>(R.id.actResFBK_txvAmostras) }
    private val txvFBK: TextView by lazy { findViewById<TextView>(R.id.actResFBK_txvFBK) }
    private val txvFBM: TextView by lazy { findViewById<TextView>(R.id.actResFBK_txvFBM) }
    private val txvResistLote: TextView by lazy { findViewById<TextView>(R.id.actResFBK_txvResistenciaLote) }
    private val btShare: ImageButton by lazy { findViewById<ImageButton>(R.id.actResFBK_btShare) }
    private val btDetalhar: Button by lazy { findViewById<Button>(R.id.actResFBK_btDetalhar) }

    private lateinit var AMOSTRAS: FloatArray
    private lateinit var FBK: FBK


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_resultado_fbk)

        AMOSTRAS = intent.getFloatArrayExtra("AMOSTRAS")
        FBK = FBK(AMOSTRAS)

        setListeners()
        setViews()
    }

    private fun setListeners() {
        btShare.setOnClickListener(this)
        btDetalhar.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    private fun setViews(){
        txvAmostras.text = "${FBK.sLista}\n\nQuantidade: ${FBK.sN} elementos"
        txvFBK.text = FBK.sFbk
        txvFBM.text = FBK.sFbm
        txvResistLote.text = FBK.sResistencia
    }

    @SuppressLint("SetTextI18n")
    private fun shareResults() {
        val shareMsg = "-[ ResistCalc App ]-\n\n" +
                "Os resultados obtidos foram:\n\n" +
                txvAmostras.text.toString() +
                "\n\nfbk,est: ${txvFBK.text} MPa" +
                "\nfbm (resistência média): ${txvFBM.text} MPa" +
                "\nResistência do lote: ${txvResistLote.text} MPa" +
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
                .putExtra("FBK", FBK))
    }

    override fun onClick(v: View?) {
        when (v) {
            btDetalhar -> detalharResultados(AMOSTRAS)
            btShare -> shareResults()
        }
    }

}
