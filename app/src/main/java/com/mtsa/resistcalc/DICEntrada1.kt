package com.mtsa.resistcalc

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.act_dic__entrada1.*


/*
     --------------------------------------------------------------------------
     ANÁLISE DE VARIÂNCIA (ANOVA) - DELINEAMENTO INTEIRAMENTE CASUALIZADO (DIC)
     --------------------------------------------------------------------------
*/
class DICEntrada1 : AppCompatActivity(), View.OnClickListener {

    private lateinit var edtxK : EditText
    private lateinit var edtxN : EditText
    private lateinit var edtxALFA : EditText
    private var _K : Int = 1
    private var _N : Int = 1
    private var _ALFA : Float = 1f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_dic__entrada1)

        initViews()
    }

    private fun initViews() {
        edtxK = findViewById(R.id.actADIC_edtxK)
        edtxN = findViewById(R.id.actADIC_edtxN)
        edtxALFA = findViewById(R.id.actADIC_edtxAlfa)

        setListeners()
    }

    private fun setListeners() {
        actADIC_btAvancar.setOnClickListener(this)
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
        }
    }

}
