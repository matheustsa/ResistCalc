package com.mtsa.resistcalc

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.TransitionManager
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView


class DetalharFBK : AppCompatActivity(), View.OnClickListener {
    
    private lateinit var layoutTxvContainer: LinearLayout
    private lateinit var txvTitle: TextView
    private lateinit var txvDescr: TextView
    private lateinit var btNext: Button
    private lateinit var btPrev: Button
    
    private var step = -1
    private val stepMAX = 7
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_detalhar_fbk)
    
        initViews()
    
    }
    
    private fun initViews() {
        
        
        layoutTxvContainer = findViewById(R.id.actDetFBK_LinearLayout)
        txvTitle = findViewById(R.id.actDetFBK_txvTitle)
        txvDescr = findViewById(R.id.actDetFBK_txvDescr)
        btNext = findViewById(R.id.actDetFBK_btNext)
        btPrev = findViewById(R.id.actDetFBK_btPrevious)
        
        btNext.setOnClickListener(this)
        btPrev.setOnClickListener(this)
    }
    
    private fun changeText(option: Boolean) {
        
        
        TransitionManager.beginDelayedTransition(layoutTxvContainer)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein)
        
        when (option) {
            true -> if (step < stepMAX) step++
            
            false -> if (step != 0) step--
            
        }
        
        when (step) {
            0 -> {
                txvTitle.visibility = View.VISIBLE
                txvDescr.visibility = View.VISIBLE
                btPrev.isEnabled = false
                txvTitle.text = getString(R.string.introducao)
                txvDescr.text = getString(R.string.fbk_introducao_description)
                
            }
            1 -> {
                txvTitle.startAnimation(fadeIn)
                txvDescr.startAnimation(fadeIn)
                btPrev.isEnabled = true
                txvTitle.text = getString(R.string.fbk_passo1)
                txvDescr.text = getString(R.string.fbk_passo1_descr)
            }
            2 -> {
                txvTitle.startAnimation(fadeIn)
                txvDescr.startAnimation(fadeIn)
                txvTitle.text = getString(R.string.fbk_psso2)
                txvDescr.text = getString(R.string.fbk_passo2_descr)
            }
            3 -> {
                txvTitle.startAnimation(fadeIn)
                txvDescr.startAnimation(fadeIn)
                txvTitle.text = getString(R.string.fbk_passo3)
                txvDescr.text = getString(R.string.fbk_passo3_descr)
            }
            4 -> {
                txvTitle.startAnimation(fadeIn)
                txvDescr.startAnimation(fadeIn)
                txvTitle.text = getString(R.string.fbk_passo4)
                txvDescr.text = getString(R.string.fbk_passo4_descr)
            }
            5 -> {
                txvTitle.startAnimation(fadeIn)
                txvDescr.startAnimation(fadeIn)
                txvTitle.text = getString(R.string.fbk_passo5)
                txvDescr.text = getString(R.string.fbk_passo5_descr)
            }
            6 -> {
                txvTitle.startAnimation(fadeIn)
                txvDescr.startAnimation(fadeIn)
                btNext.isEnabled = true
                txvTitle.text = getString(R.string.fbk_passo6)
                txvDescr.text = getString(R.string.fbk_passo6_descr)
            }
            7 -> {
                txvTitle.startAnimation(fadeIn)
                txvDescr.startAnimation(fadeIn)
                btNext.isEnabled = false
                txvTitle.text = getString(R.string.fbk_passo7)
                txvDescr.text = getString(R.string.fbk_passo7_descr)
                
            }
        }
        
    }
    
    override fun onClick(v: View?) {
        when (v) {
            btNext -> changeText(true)
            btPrev -> changeText(false)
        }
    }
}
