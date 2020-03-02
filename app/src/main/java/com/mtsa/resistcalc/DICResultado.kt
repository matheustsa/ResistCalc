package com.mtsa.resistcalc

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.act_dic__resultado.*

class DICResultado : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_dic__resultado)

        val DIC = intent.getSerializableExtra("DIC") as DIC

        actDIC_txvResultado.text = "k: ${DIC.sK}\nn: ${DIC.sN}\nalfa: ${DIC.sALFA}\n\nTs: ${DIC.sTs}"
    }
}
