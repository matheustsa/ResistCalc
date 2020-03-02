package com.mtsa.resistcalc

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(), View.OnClickListener {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        //Esconder AppTitleBar
        supportActionBar?.hide()
        
        setListeners()
    }
    
    private fun setListeners() {
        actMain_btFBK.setOnClickListener(this)
        actMain_btFPK.setOnClickListener(this)
        actMain_btADIC.setOnClickListener(this)
        actMain_btSobre.setOnClickListener(this)
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        when (v) {
            actMain_btFBK -> startActivity(Intent(this, Entrada::class.java).putExtra("OP", "FBK"))
            actMain_btFPK -> startActivity(Intent(this, Entrada::class.java).putExtra("OP", "FPK"))
            actMain_btADIC -> startActivity(Intent(this, DICEntrada1::class.java))
            actMain_btSobre -> toast("Em desenvolvimento...")
        }
    }
}
