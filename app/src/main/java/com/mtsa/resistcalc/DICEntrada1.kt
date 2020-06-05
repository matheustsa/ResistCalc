package com.mtsa.resistcalc

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.act_dic__entrada1.*


/*
     --------------------------------------------------------------------------
     ANÁLISE DE VARIÂNCIA (ANOVA) - DELINEAMENTO INTEIRAMENTE CASUALIZADO (DIC)
     --------------------------------------------------------------------------
*/
class DICEntrada1 : AppCompatActivity(), View.OnClickListener {


    private val edtxK : EditText by lazy { findViewById<EditText>(R.id.actADIC_edtxK) }
    private val edtxN : EditText by lazy { findViewById<EditText>(R.id.actADIC_edtxN) }
    private val edtxALFA : EditText by lazy { findViewById<EditText>(R.id.actADIC_edtxAlfa) }

    private val btAvancar: Button by lazy { findViewById<Button>(R.id.actADIC_btAvancar) }
    private val btExemplo: Button by lazy { findViewById<Button>(R.id.actADIC_btExemplo) }

    private var _K : Int = 1
    private var _N : Int = 1
    private var _ALFA : Float = 1f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_dic__entrada1)

        initViews()
    }

    private fun initViews() {

        edtxALFA.inputType = InputType.TYPE_CLASS_NUMBER or
                InputType.TYPE_NUMBER_FLAG_DECIMAL or
                InputType.TYPE_NUMBER_FLAG_SIGNED

        btAvancar.setOnClickListener(this)
        btExemplo.setOnClickListener(this)
    }

    private fun getValues() {
        if (!edtxK.text.isNullOrBlank())
            _K = edtxK.text.toString().toInt()
        if (!edtxN.text.isNullOrBlank())
            _N = edtxN.text.toString().toInt()
        if (!edtxALFA.text.isNullOrBlank())
            _ALFA = edtxALFA.text.toString().toFloat()
    }

    override fun onClick(v: View?) {
        when (v) {
            actADIC_btAvancar -> {
                getValues()
                println("K: $_K  -  N: $_N  -  ALFA: $_ALFA")
                startActivity(Intent(this, DICEntrada2::class.java)
                    .putExtra("DIC_K", _K)
                    .putExtra("DIC_N", _N)
                    .putExtra("DIC_ALFA", _ALFA))
            }

            actADIC_btExemplo -> {
                val lista = listOf(
                    listOf(20.4, 22.6, 23.4, 24.6, 22.4, 22.6, 34.6, 25.6, 26.1, 29.2),
                    listOf(18.6, 18.9, 19.6, 19.2, 20.4, 24.6, 23.1, 22.1, 18.5, 19.1),
                    listOf(18.6, 18.9, 19.6, 22.4, 22.6, 26.4, 27.4, 26.4, 26.4, 22.4),
                    listOf(17.5, 18.5, 16.2, 14.3, 18.9, 19.6, 14.6, 22.5, 21.3, 19.5),
                    listOf(21.4, 21.8, 22.6, 22.4, 22.6, 34.6, 26.8, 24.6, 24.6, 24.5),
                    listOf(22.4, 22.6, 34.6, 24.6, 22.6, 23.6, 18.6, 18.9, 19.6, 24.6)
                )

                val dic = DIC(6, 10, 0.05F, lista)

                startActivity(
                    Intent(this, DICResultado2::class.java)
                        .putExtra("DIC", dic))
            }
        }
    }

}
