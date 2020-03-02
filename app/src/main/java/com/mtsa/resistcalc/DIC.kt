package com.mtsa.resistcalc

import java.io.Serializable

class DIC(_K: Int, _N: Int, _ALFA: Float, _Tratamentos: MutableList<FloatArray>) : Serializable {


    val sK = _K.toString()
    val sN = _N.toString()
    val sALFA = _ALFA.toString()
    val sTs = _Tratamentos.forEach { it.asList() }

    /*

   TODO: média das amostras
   TODO: desvio padrão
   TODO: variância
   TODO: coeficiente de variação (desvio padrão/média) * 100
   TODO: média global

    */

}