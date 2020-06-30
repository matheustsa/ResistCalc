package com.mtsa.resistcalc

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.mtsa.utils.Utils
import org.jetbrains.anko.alert


/*
     --------------------------------------------------------------------------
     ANÁLISE DE VARIÂNCIA (ANOVA) - DELINEAMENTO INTEIRAMENTE CASUALIZADO (DIC)
     --------------------------------------------------------------------------
*/
class DICEntrada1 : AppCompatActivity(), View.OnClickListener {

    private val edtxK : EditText by lazy { findViewById<EditText>(R.id.edtxK) }
    private val edtxN : EditText by lazy { findViewById<EditText>(R.id.edtxN) }
    private val edtxALFA : EditText by lazy { findViewById<EditText>(R.id.edtxAlfa) }

    private val btAvancar: Button by lazy { findViewById<Button>(R.id.btAvancar) }
    private val btExemplo: Button by lazy { findViewById<Button>(R.id.btExemplo) }

    private var k : Int = 1
    private var n : Int = 1
    private var alfa : Float = 1f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_dic__entrada1)

        initViews()
    }

    private fun initViews() {

        edtxALFA.inputType = InputType.TYPE_CLASS_NUMBER or
                InputType.TYPE_NUMBER_FLAG_DECIMAL or
                InputType.TYPE_NUMBER_FLAG_SIGNED

        btAvancar.setOnClickListener(this)
        btExemplo.setOnClickListener(this)
    }

    private fun getValues() {
        val sK = edtxK.text
        val sN = edtxN.text
        val sALFA = edtxALFA.text


        if (sK.isNullOrBlank() || sN.isNullOrBlank() || sALFA.isNullOrBlank())
            alert("Você precisa informar todos os campos antes de continuar") {
                positiveButton("Vou corrigir"){}
            }.show()
        else {
            val k = sK.toString().toInt()
            val n = sN.toString().toInt()
            val alfa = sALFA.toString().toFloat()

            // TODO: 24/06/2020 testar se isso funciona
            when {
                k<2 -> alert("Você precisa de pelo menos 2 tratamentos (k) para um cálculo correto") {
                    positiveButton("Vou corrigir"){}
                }.show()

                n<2 -> alert("Sua amostra deve ter pelo menos 2 repetições (n)") {
                    positiveButton("Vou corrigir"){}
                }.show()

                alfa<0.05f -> alert("O nível de significância (alfa) mínimo é [0.05]") {
                    positiveButton("Vou corrigir"){}
                }.show()

                else -> sendValues()
            }
        }
    }

    private fun sendValues() {
      startActivity(Intent(this, DICEntrada2::class.java)
            .putExtra("DIC_K", edtxK.text.toString().toInt())
            .putExtra("DIC_N", edtxN.text.toString().toInt())
            .putExtra("DIC_ALFA", edtxALFA.text.toString().toFloat()))
    }

    override fun onClick(v: View?) {
        when (v) {
            btAvancar -> getValues()
            btExemplo -> {
                startActivity(Intent(this, DICResultado2::class.java)
                    .putExtra("DIC",
                        DIC2(6, 10, 0.05F, Utils.listaExemplo("dic"))))
            }
        }
    }

}
