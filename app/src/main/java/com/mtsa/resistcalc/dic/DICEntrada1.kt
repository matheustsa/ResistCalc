package com.mtsa.resistcalc.dic

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.mtsa.resistcalc.R
import com.mtsa.utils.Utils

/*
     --------------------------------------------------------------------------
     ANÁLISE DE VARIÂNCIA (ANOVA) - DELINEAMENTO INTEIRAMENTE CASUALIZADO (DIC)
     --------------------------------------------------------------------------
*/
class DICEntrada1 : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_dic__entrada_1)

        initViews()
    }

    private val btAvancar: Button by lazy { findViewById<Button>(R.id.btAvancar) }
    private val btExemplo: Button by lazy { findViewById<Button>(R.id.btExemplo) }

    private val spnK: Spinner by lazy { findViewById<Spinner>(R.id.spnK) }
    private val spnN: Spinner by lazy { findViewById<Spinner>(R.id.spnN) }
    private val spnAlfa: Spinner by lazy { findViewById<Spinner>(R.id.spnAlfa) }

    private fun initViews() {
        btAvancar.setOnClickListener(this)
        btExemplo.setOnClickListener(this)
    }

    private fun sendValues() {
        startActivity(
            Intent(this, DICEntrada2::class.java)
                .putExtra("DIC_K", spnK.selectedItem.toString().toInt())
                .putExtra("DIC_N", spnN.selectedItem.toString().toInt())
                .putExtra("DIC_ALFA", spnAlfa.selectedItem.toString().toFloat())
        )
    }

    override fun onClick(v: View?) {
        when (v) {
            btAvancar -> sendValues()
            btExemplo -> {
                startActivity(
                    Intent(this, DICResultado2::class.java)
                        .putExtra(
                            "DIC",
                            DIC2(
                                6,
                                10,
                                0.05F,
                                Utils.listaExemplo("anova_dic")
                            )
//                            DIC2(
//                                3,
//                                10,
//                                0.05F,
//                                Utils.listaExemplo("anova_dic2")
//                            )
                        )
                )
            }
        }
    }
}