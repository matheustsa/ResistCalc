package com.mtsa.resistcalc

import java.io.Serializable

class DIC(_K: Int, _N: Int, _ALFA: Float, LIST: List<FloatArray>) : Serializable {

    val k = _K
    val n = _N
    val alfa = _ALFA
    val LIST = LIST

    val sK = _K.toString()
    val sN = _N.toString()
    val sALFA = _ALFA.toString()
    val sLIST = LIST

}

class DIC2(val k: Int, val n: Int, val alfa: Float, val lista: List<List<Double>>) : Serializable {

}