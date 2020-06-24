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
            k = sK.toString().toInt()
            n = sN.toString().toInt()
            alfa = sALFA.toString().toFloat()

            startActivity(Intent(this, DICEntrada2::class.java)
                .putExtra("DIC_K", k)
                .putExtra("DIC_N", n)
                .putExtra("DIC_ALFA", alfa))
        }
    }

    /*
    private fun alertaCampoVazio(campo: Int) {
        alert(
            when (campo) {
                1 -> "Você precisa informar a quantidade de tratamentos (k) no primeiro campo de texto"
                2 -> "Você precisa informar a quantidade de repetições (n) no segundo campo de texto"
                3 -> "Você precisa informar o nível de significância (alfa) no terceiro campo de texto"
                else -> ""
            }
        ) {
            title = "Dados incompletos"
            positiveButton("Vou corrigir") {}
        }.show()
    }
     */

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
