package com.mtsa.resistcalc

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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
        actMain_btSobre.setOnClickListener(this)
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        when (v) {
            actMain_btFBK -> startActivity(Intent(this, FBK::class.java))
            actMain_btFPK -> startActivity(Intent(this, FPK::class.java))
            actMain_btSobre -> toast("Em desenvolvimento...")
        }
    }
}
