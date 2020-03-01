package com.mtsa.resistcalc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import com.mtsa.utils.Utils


class DetalharFBK : AppCompatActivity(), View.OnClickListener {
    
    private lateinit var txvTitle: TextView
    private lateinit var txvDescr: TextView
    private lateinit var btNext: Button
    private lateinit var btPrev: Button
    
    private lateinit var FBK: FBK
    
    private var step = -1
    private val stepMAX = 7
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_detalhar_fbk)
    
        initViews()
    }
    
    private fun initViews() {
        
        txvTitle = findViewById(R.id.actDetFBK_txvTitle)
        txvDescr = findViewById(R.id.actDetFBK_txvDescr)
        btNext = findViewById(R.id.actDetFBK_btNext)
        btPrev = findViewById(R.id.actDetFBK_btPrevious)
        
        btNext.setOnClickListener(this)
        btPrev.setOnClickListener(this)
        
        FBK = intent.getSerializableExtra("FBK") as FBK
    }
    
    
    private fun changeStep(option: Boolean) {
    
        when (option) {
            true -> if (step < stepMAX) step++
        
            false -> if (step != 0) step--
        
        }
    
        // set text animation
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein)
        
        val sSoma2 = Utils.twoDec(FBK.soma*2)
        val v1 = FBK.lista[0].toString()
        val v2 = FBK.lista[1].toString()
        val v3 = FBK.lista[2].toString()
        
        when (step) {
            0 -> {
                txvTitle.visibility = View.VISIBLE
                txvDescr.visibility = View.VISIBLE
                btPrev.isEnabled = false
                changeText(fadeIn, R.string.introducao, R.string.fbk_introducao_description)
                
            }
            1 -> {
                btPrev.isEnabled = true
                changeText(fadeIn, R.string.fbk_passo1)
                txvDescr.text = getString(R.string.fbk_passo1_descr, FBK.sI)
            }
            2 -> {
                changeText(fadeIn, R.string.fbk_passo2, R.string.fbk_passo2_descr)
                txvDescr.text = getString(R.string.fbk_passo2_descr, v1, v2 ,v3, FBK.sSoma, sSoma2)
            }
            3 -> {
                changeText(fadeIn, R.string.fbk_passo3)
                txvDescr.text = getString(R.string.fbk_passo3_descr, sSoma2, FBK.sI, (FBK.soma*2/FBK.i))
            }
            4 -> {
                changeText(fadeIn, R.string.fbk_passo4)
                txvDescr.text = getString(R.string.fbk_passo4_descr, FBK.sFbi)
            }
            5 -> {
                changeText(fadeIn, R.string.fbk_passo5)
                txvDescr.text = getString(R.string.fbk_passo5_descr, sSoma2, FBK.sFbi, FBK.sFbk)
            }
            6 -> {
                btNext.isEnabled = true
                changeText(fadeIn, R.string.fbk_passo6)
                txvDescr.text = getString(R.string.fbk_passo6_descr, Utils.twoDec(FBK.lista.sum()), FBK.sN, FBK.sFbm)
            }
            7 -> {
                btNext.isEnabled = false
                changeText(fadeIn, R.string.fbk_passo7, R.string.fbk_passo7_descr)
                
            }
        }
        
    }
    
    
    private fun animate(animation: Animation, vararg views: View) {
        for (v in views)
            v.startAnimation(animation)
    }
    
    private fun changeText(animation: Animation, title: Int) {
        animate(animation, txvTitle, txvDescr)
        txvTitle.text = getString(title)
    }
    private fun changeText(animation: Animation, title: Int, description: Int) {
        animate(animation, txvTitle, txvDescr)
        txvTitle.text = getString(title)
        txvDescr.text = getString(description)
    }
    
    override fun onClick(v: View?) {
        when (v) {
            btNext -> changeStep(true)
            btPrev -> changeStep(false)
        }
    }
}
