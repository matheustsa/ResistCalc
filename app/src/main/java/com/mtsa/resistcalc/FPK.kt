package com.mtsa.resistcalc

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import org.jetbrains.anko.alert

class FPK : AppCompatActivity(), View.OnClickListener {
    
    private lateinit var edtxEntradas: EditText
    private lateinit var btCalcular: Button
    private lateinit var btExemplo: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fpk)
        
        initViews()
    }
    
    private fun initViews() {
        edtxEntradas = findViewById(R.id.actFPK_edtxEntradas)
        btCalcular = findViewById(R.id.actFPK_btCalcular)
        btExemplo = findViewById(R.id.actFPK_btExemplo)
        
        btCalcular.setOnClickListener(this)
        btExemplo.setOnClickListener(this)
    
    }
    
    private fun getValues(entrada: String): FloatArray {
        
        val lista = entrada.split(";", "\n").sorted()
        
        val amostras = FloatArray(lista.count())
        for ((index, value) in lista.withIndex()) {
            if (value.contains(","))
                amostras[index] = value.replace(",", ".").toFloat()
            else
                amostras[index] = value.toFloat()
        }
        
        // por algum motivo essa bosta joga o primeiro valor pra última posição, então tem que dar sort() de novo
        return amostras.sortedArray()
    }
    
    private fun showResults() {
        startActivity(
            Intent(this, ResultadoFPK::class.java)
                .putExtra("Amostras", getValues(edtxEntradas.text.toString()))
        )
    }
    
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.actFPK_btCalcular -> {
                val quantidade = getValues(edtxEntradas.text.toString()).count()
                if (quantidade > 5) {
                    alert("Você digitou $quantidade elementos.\nEsse é o tamanho correto da sua amostra?") {
                        title = "Só confirmando..."
                        positiveButton("Está correto") { showResults() }
                        negativeButton("Preciso corrigir") {}
                    }.show()
                } else {
                    alert("Para um cálculo correto, você precisa informar pelo menos 6 valores...") {
                        title = "ERRO!"
                        positiveButton("CORRIGIR") {}
                    }.show()

//                    // custom dialog
//                    val dialog = Dialog(this)
//                    dialog.setContentView(R.layout.error_alert_dialog)
//                    dialog.show()
                }
                
            }
            R.id.actFPK_btExemplo -> {
                val exemplo = "12,4; 12,4; 12,6; 13,5; 15,4; 16,4; 17,4"
                startActivity(
                    Intent(this, ResultadoFPK::class.java)
                        .putExtra("Amostras", getValues(exemplo))
                )
            }
        }
    }
}
