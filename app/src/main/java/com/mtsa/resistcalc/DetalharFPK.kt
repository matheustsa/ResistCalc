package com.mtsa.resistcalc

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.act_detalhar_fpk.*
import kotlin.math.abs
import kotlin.math.sqrt

class DetalharFPK : AppCompatActivity(), View.OnClickListener {
    
    var counter = 0
    val LAST = 4
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_detalhar_fpk)
    
        actDetFPK_btAvancar.setOnClickListener(this)
    
        val amostras = intent.getFloatArrayExtra("AMOSTRAS")
        val soma = amostras.sum()
        val size = amostras.count()
        val media = intent.getFloatExtra("MEDIA", 0f)
    
        val v1 = sqrt(abs(amostras[0] - media))
        val v2 = sqrt(abs(amostras[1] - media))
        val v3 = sqrt(abs(amostras[2] - media))
        val vt = intent.getFloatExtra("VARIANCIA", 0f)
    
        val t_critico = intent.getFloatExtra("TCRITICO", 0f)
        val fpk = media - (t_critico * sqrt(vt))
    
        actDetFPK_txvP1Desc.text = resources.getString(R.string.fpk_passo1_descr, soma, size, media)
        actDetFPK_txvP2Desc.text = resources.getString(
            R.string.fpk_passo2_descr,
            amostras[0], media, v1, amostras[1], media, v2, amostras[2], media, v3, v1, v2, v3, vt
        )
        actDetFPK_txvP3Desc.text = resources.getString(R.string.fpk_passo3_descr, sqrt(vt))
        actDetFPK_txvP4Desc.text = resources.getString(R.string.fpk_passo4_descr, t_critico)
        actDetFPK_txvP5Desc.text =
            resources.getString(R.string.fpk_passo5_descr, media, t_critico, sqrt(vt), fpk)
    }
    
    override fun onClick(v: View?) {
        when (v) {
            actDetFPK_btAvancar -> {
                
                if (counter < LAST) {
                    counter++
                    ViewFlipper.displayedChild = counter
                } else {
                    counter = 0
                    ViewFlipper.displayedChild = counter
                }
                
                println("Counter: $counter")
            }
        }
    }
}
