package com.mtsa.resistcalc

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.act_entrada.*
import org.jetbrains.anko.alert

class Entrada : AppCompatActivity(), View.OnClickListener {
    
    private lateinit var edtxEntradas: EditText
    private lateinit var btCalcular: Button
    private lateinit var btExemplo: Button
    private lateinit var exemplo: String
    private lateinit var OPERATION: String
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_entrada)
        
        OPERATION = intent.getStringExtra("OP")
        
        initViews()
        
        when (OPERATION) {
            "FBK" -> {
                actENTRY_txvTitle.text = resources.getString(R.string.fbk_title)
                btCalcular.setBackgroundResource(R.drawable.fbk_calcular)
                exemplo =
                    "12,4; 12,4; 12,4; 12,6; 13,5; 13,5; 13,9; 14,2; 14,2; 15,1; 15,4; 16,2; 16,4; 17,4"
            }
            "FPK" -> {
                actENTRY_txvTitle.text = resources.getString(R.string.fpk_title)
                btCalcular.setBackgroundResource(R.drawable.fpk_calcular)
                exemplo = "12,4; 12,4; 12,6; 13,5; 15,4; 16,4; 17,4"
            }
            
        }
        
    }
    
    private fun initViews() {
        edtxEntradas = findViewById(R.id.actENTRY_edtxEntradas) as EditText
        btCalcular = findViewById(R.id.actENTRY_btCalcular)
        btExemplo = findViewById(R.id.actENTRY_btExemplo)
        
        btCalcular.setOnClickListener(this)
        btExemplo.setOnClickListener(this)
    }
    
    private fun getValues(entrada: String): FloatArray {
        
        if (!entrada.isBlank()) {
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
        } else {
            return FloatArray(entrada.count())
        }
        
    }
    
    private fun showResults() {
        startActivity(
            Intent(this, ResultadoFBK::class.java)
                .putExtra("Amostras", getValues(edtxEntradas.text.toString()))
        )
    }
    
    
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.actENTRY_btCalcular -> {
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
                }
                
            }
            R.id.actENTRY_btExemplo -> {
                when (OPERATION) {
                    "FBK" -> startActivity(
                        Intent(this, Resultado::class.java)
                            .putExtra("OP", "FBK")
                            .putExtra("Amostras", getValues(exemplo))
                    )
                    
                    "FPK" -> startActivity(
                        Intent(this, Resultado::class.java)
                            .putExtra("OP", "FPK")
                            .putExtra("Amostras", getValues(exemplo))
                    )
                }
            }
        }
    }
}
