package com.mtsa.resistcalc

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btFBK: Button
    private lateinit var btFPK: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        btFBK = findViewById(R.id.actMain_btFBK)
        btFPK = findViewById(R.id.actMain_btFPK)

        btFBK.setOnClickListener(this)
        btFPK.setOnClickListener(this)
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.actMain_btFBK -> startActivity(Intent(this, FBK::class.java))
            R.id.actMain_btFPK -> startActivity(Intent(this, FPK::class.java))
        }
    }
}
