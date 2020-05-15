package com.mtsa.resistcalc

import android.os.Bundle
import android.text.BoringLayout
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.mtsa.utils.Utils
import kotlinx.android.synthetic.main.act_detalhar_fbk2.*
import kotlinx.android.synthetic.main.act_detalhar_fpk.*


class DetalharFBK : AppCompatActivity(), View.OnClickListener {

    private lateinit var layout: ConstraintLayout

    private lateinit var txvTitle: TextView
    private lateinit var txvDescr: TextView
    private lateinit var txvOla: TextView
    private lateinit var txvOla2: TextView
    private lateinit var txvPassos: TextView
    private lateinit var btNext: Button
    private lateinit var btPrev: ImageButton

    private lateinit var FBK: FBK

    private lateinit var fadeIn: Animation

    private var step = -1
    private val stepMAX = 7
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_detalhar_fbk2)
    
        initViews()

    }
    
    private fun initViews() {

        layout = findViewById(R.id.actDetFBK_constraintLayout)
        
        txvTitle = findViewById(R.id.actDetFBK_txvTitle)
        txvDescr = findViewById(R.id.actDetFBK_txvDescr)
        txvOla = findViewById(R.id.actDetFBK_txvOla)
        txvOla2 = findViewById(R.id.actDetFBK_txvOla2)

        txvPassos = findViewById(R.id.actDetFBK_txvPassos)

        btNext = findViewById(R.id.actDetFBK_btNext)
        btPrev = findViewById(R.id.actDetFBK_btPrevious)
        btNext.setOnClickListener(this)
        btPrev.setOnClickListener(this)
        
        FBK = intent.getSerializableExtra("FBK") as FBK

        // set text animation
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein)

        makeVisible(false, txvTitle, txvDescr, txvPassos, btPrev)

    }
    
    
    private fun changeStep(option: Boolean) {

        when (option) {
            true -> if (step < stepMAX) step++
            false -> if (step != 0) step--
        }

        animate(fadeIn, txvTitle, txvDescr)

        txvPassos.text = "$step de $stepMAX"

        val sSoma2 = Utils.twoDec(FBK.soma*2)
        val v1 = FBK.lista[0].toString()
        val v2 = FBK.lista[1].toString()
        val v3 = FBK.lista[2].toString()

        when (step) {
            0 -> {
                makeVisible(false, txvOla, txvOla2, btPrev, txvPassos)
                makeVisible(true, txvTitle, txvDescr)
                btPrev.isEnabled = false

                changeTitle(R.string.introducao)
                changeDescription(R.string.fbk_introducao_description)
            }
            1 -> {
//                animate(fadeIn, btPrev, txvPassos)
                makeVisible(true, btPrev, txvPassos)
                btPrev.isEnabled = true

                changeTitle(R.string.fbk_passo1)
//                changeDescription(R.string.fbk_passo1_descr, FBK.sI)
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
    
    private fun makeVisible(option: Boolean, vararg views: View) {
        for (v in views) {
            if (option) v.visibility = View.VISIBLE else v.visibility = View.GONE
            v.invalidate()
        }
    }

    private fun animate(animation: Animation, vararg views: View) {
        for (v in views)
            v.startAnimation(animation)
    }
    
    private fun changeText(animation: Animation, title: Int) {
        animate(animation, txvTitle)
        txvTitle.text = getString(title)
    }
    private fun changeText(animation: Animation, title: Int, description: Int) {
        animate(animation, txvTitle, txvDescr)
        txvTitle.text = getString(title)
        txvDescr.text = getString(description)
    }

    private fun changeTitle(stringXML: Int) {
        txvTitle.text = getString(stringXML)
    }

    // TODO: Precisa descobrir como utilizar um array para as variáveis
    private fun changeDescription(stringXML: Int, vararg varibles: Any) {
        txvDescr.text = getString(stringXML, varibles)
    }
    
    override fun onClick(v: View?) {
        when (v) {
            btNext -> changeStep(true)
            btPrev -> changeStep(false)
        }
    }
}
