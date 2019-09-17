package com.mtsa.resistcalc

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_amostras.*

class Amostras : AppCompatActivity(), View.OnClickListener {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amostras)
        
        val opcaoEscolhida = intent.getStringExtra("calculo")
        setViews(opcaoEscolhida)
    }
    
    @SuppressLint("ResourceAsColor")
    private fun setViews(opcao: String) {
        
        when (opcao) {
            "FBK" -> {
                actAmos_title.text = getString(R.string.fbk_title)
                actAmos_btCalcular.text = "dakjsdha"
                actAmos_btCalcular.setBackgroundColor(Color.BLUE)
                actAmos_btCalcular.visibility = View.GONE
                actAmos_btCalcular.visibility = View.VISIBLE
            }
            "FPK" -> {
                actAmos_title.text = getString(R.string.fpk_title)
            }
        }
    }
    
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
