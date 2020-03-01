package com.mtsa.resistcalc

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.act_detalhar_fpk.*
import kotlin.math.abs
import kotlin.math.sqrt

class DetalharFPK : AppCompatActivity(), View.OnClickListener {
    
    var child = 0
    val childMAX = 4
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_detalhar_fpk)
    
        actDetFPK_btAvancar.setOnClickListener(this)
        actDetFPK_btVoltar.setOnClickListener(this)
    
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
    
    private fun changeViews(direction: Boolean) {
    
        val slideFromLeft = AnimationUtils.loadAnimation(this, R.anim.slide_left_to_right)
        val slideFromRight = AnimationUtils.loadAnimation(this, R.anim.slide_right_to_left)

        when (direction) {
            true -> if (child < childMAX) {
                child++
                ViewFlipper.startAnimation(slideFromLeft)
            }
            
            false -> if (child != 0) {
                child--
                ViewFlipper.startAnimation(slideFromRight)
            }
        }
    
        // This method will be executed once the timer (in milliseconds) is over
        Handler().postDelayed({ViewFlipper.displayedChild = child},350 )
        
        when (child) {
            0 -> actDetFPK_btVoltar.isEnabled = false
            1 -> actDetFPK_btVoltar.isEnabled = true
            3 -> actDetFPK_btAvancar.isEnabled = true
            4 -> actDetFPK_btAvancar.isEnabled = false
        }
    }
    
    override fun onClick(v: View?) {
        when (v) {
            actDetFPK_btAvancar -> changeViews(true)
            actDetFPK_btVoltar -> changeViews(false)
        }
    }
}
