package com.mtsa.resistcalc

import com.mtsa.utils.Utils
import org.apache.commons.math3.distribution.FDistribution
import java.io.Serializable
import kotlin.math.pow

class DIC(val k: Int, val n: Int, val alfa: Float, val lista: List<List<Double>>) : Serializable

class DIC2(val k: Int, val n: Int, val alfa: Float, val lista: List<List<Double>>) : Serializable {
    val gl1 = k-1
    val gl2 = k*(n-1)
    val glTotal = gl1+gl2

    val media = mutableListOf<Double>()
    val desvioPadrao = mutableListOf<Double>()
    val variancia = mutableListOf<Double>()
    val coeficienteVariacao = mutableListOf<Double>()


    init {
        for ((index, T) in lista.withIndex()) {
            media.add(Utils.roundDec(T.average(), 4))
            desvioPadrao.add(Utils.desvioPadrao(T))
            variancia.add(Utils.variancia(T))
            coeficienteVariacao.add(Utils.coefVariacao(media[index], desvioPadrao[index]))
        }
    }


    val mediaGlobal = Utils.roundDec((lista.sumByDouble { it.sum() }/(k*n)), 4)
//        println("=================== MEDIA GLOBAL:" + lista.sumByDouble { it.sum() })

    val QM_Dentro = Utils.roundDec(variancia.sum() / k, 4)
//       N * (m0 - m_global)^2 + (m1 - m_global)^2 + ...

    var QM_Entre = 0.0

    init {
        for (value in media) {
            QM_Entre += (value - mediaGlobal).pow(2)
        }
        QM_Entre *= n
        QM_Entre = Utils.roundDec(QM_Entre, 4)
    }

    val SQ_Dentro = Utils.roundDec(QM_Dentro * gl1, 4)
    val SQ_Entre = Utils.roundDec(QM_Entre * gl2, 4)
    val SQ_Total = Utils.roundDec(SQ_Dentro + SQ_Entre, 4)

    val F_Calculado = Utils.roundDec(QM_Entre / QM_Dentro, 4)
    val F_Critico = Utils.roundDec(FDistribution(gl1.toDouble(), gl2.toDouble()).inverseCumulativeProbability(1.0 - alfa), 4)

    val julgamento = if (F_Calculado > F_Critico) "Rejeita-se H0" else "Rejeita-se H1"

}

