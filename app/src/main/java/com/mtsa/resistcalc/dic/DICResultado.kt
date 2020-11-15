package com.mtsa.resistcalc.dic

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mtsa.resistcalc.R
import com.mtsa.utils.Utils
import org.apache.commons.math3.distribution.FDistribution
import kotlin.math.pow

class DICResultado : AppCompatActivity() {

    private lateinit var txvRes: TextView
    private lateinit var txvTrat: TextView

    private var string = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_dic__resultado)

//        calcularDIC()
    }


    @SuppressLint("SetTextI18n")
    private fun calcularDIC() {
        val DIC = intent.getSerializableExtra("DIC") as DIC

        val k = DIC.k
        val n = DIC.n
        val alfa = DIC.alfa
        val lista2 = DIC.lista

        val gl1 = k-1
        val gl2 = k*(n-1)
        val glTotal = gl1+gl2

        val media = mutableListOf<Double>()
        val desvioPadrao = mutableListOf<Double>()
        val variancia = mutableListOf<Double>()
        val coeficienteVariacao = mutableListOf<Double>()

        for ((index, T) in lista2.withIndex()) {
            media.add(Utils.roundDec(T.average(), 4))
            desvioPadrao.add(Utils.desvioPadrao(T))
            variancia.add(Utils.variancia(T))
            coeficienteVariacao.add(Utils.coefVariacao(media[index], desvioPadrao[index]))
        }

        val mediaGlobal = Utils.roundDec((lista2.sumByDouble { it.sum() }/(k*n)), 4)
        println("=================== MEDIA GLOBAL:" + lista2.sumByDouble { it.sum() })

        val QM_Dentro = Utils.roundDec(variancia.sum() / k, 4)
//       N * (m0 - m_global)^2 + (m1 - m_global)^2 + ...

        var QM_Entre = 0.0
        for (value in media) {
            QM_Entre += (value - mediaGlobal).pow(2)
        }
        QM_Entre *= n
        QM_Entre = Utils.roundDec(QM_Entre, 4)

        val F_Calculado = Utils.roundDec(QM_Entre / QM_Dentro, 4)
        val F_Critico = Utils.roundDec(FDistribution(gl1.toDouble(), gl2.toDouble()).inverseCumulativeProbability(1.0 - alfa), 4)


//        txvRes = findViewById(R.id.actDIC_txvResultado)
//        txvTrat = findViewById(R.id.actDIC_txvTratamentos)

        txvRes.text = "k: $k\nn: $n\nalfa: $alfa"

        val results = "k: $k\nn: $n\nalfa: $alfa\n\n" +
                "gl1: $gl1\ngl2: $gl2\nglTotal: $glTotal\n" +
                "\n" +
                "media: $media\n" +
                "desvio padrão: $desvioPadrao\n" +
                "variância: $variancia\n" +
                "coef. variação: $coeficienteVariacao\n" +
                "\n" +
                "media global: $mediaGlobal\n" +
                "QM DENTRO: $QM_Dentro\n" +
                "QM ENTRE: $QM_Entre\n" +
                "\n" +
                "Fcrit: $F_Critico\n" +
                "Fcalc: $F_Calculado\n\n" +
                "H0 - Médias Iguais\n" +
                "H1 - Médias diferentes"

        txvTrat.text = ""
        txvRes.text = results + "\n\n" + getValues(lista2) + "\n\n" + getHipotese(F_Calculado, F_Critico)
    }
    
    private fun getValues(lista: List<List<Double>>): String {
        for ((index, value) in lista.withIndex())
            string += "T${index+1} - " + value.toString() + "\n"

        return string
    }

    private fun getHipotese(fcalc: Double, fcrit: Double): String {
        return if (fcalc > fcrit) "H0 REJEITADA" else "H1 REJEITADA"
    }
}
