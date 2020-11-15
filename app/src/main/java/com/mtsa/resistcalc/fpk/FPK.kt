package com.mtsa.resistcalc.fpk

import com.mtsa.utils.Utils
import java.io.Serializable

class FPK (
    val amostras: FloatArray
) : Serializable {
    val gl = amostras.count()
    //        val alpha = 0.2     //valor fixo
    val t_student = doubleArrayOf(
        1.3764,
        1.0607,
        0.9785,
        0.9410,
        0.9195,
        0.9057,
        0.8960,
        0.8889,
        0.8834,
        0.8791,
        0.8755,
        0.8726,
        0.8702,
        0.8681,
        0.8662,
        0.8647,
        0.8633,
        0.8620,
        0.8610,
        0.8600,
        0.8591,
        0.8583,
        0.8575,
        0.8569,
        0.8562,
        0.8557,
        0.8551,
        0.8546,
        0.8542,
        0.8538
    )

    // Calcular Desvio Padrão
    // Passo 1 - Calcular a Média
    val media = amostras.average()

    // Passo 2 - Calcular a diferença entre as resistências e a média (n-1), elevando ao quadrado cada elemento
    val variancia = Utils.variancia(amostras)

    // Passo 3 - Extrair a raiz da média das variâncias
    val desvio = Utils.desvioPadrao(amostras)

    // Calcula t_crítico
    val t_critico = t_student[gl - 1]

    // Calcula fpk,est (media das peças - (valor da tabela * desvio))
    val fpk = media - (t_critico * desvio)


    val sLista = amostras.contentToString()
        .replace(", ", "\t\t")
        .replace("[","")
        .replace("]","")
    val sMedia = Utils.twoDec(media)
    val fMedia = media.toFloat()
    val sVariancia = Utils.twoDec(variancia)
    val fVariancia = variancia.toFloat()
    val sDesvio = Utils.twoDec(desvio)
    val sTcrit = t_critico.toString()
    val fTcrit = t_critico.toFloat()
    val sFPK = Utils.twoDec(fpk)
}