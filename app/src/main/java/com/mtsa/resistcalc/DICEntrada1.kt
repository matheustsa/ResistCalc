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
    private lateinit var _K : String
    private lateinit var _N : String
    private lateinit var _ALFA : String

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
        _K = edtxK.text.toString()
        _N = edtxN.text.toString()
        _ALFA = edtxALFA.text.toString()

    }

    override fun onClick(v: View?) {
        when (v) {
            actADIC_btAvancar -> {
                getValues()
                startActivity(Intent(this, DICEntrada2::class.java)
                    .putExtra("DIC_K", _K)
                    .putExtra("DIC_N", _N)
                    .putExtra("DIC_ALFA", _ALFA))
            }
        }
    }

}
