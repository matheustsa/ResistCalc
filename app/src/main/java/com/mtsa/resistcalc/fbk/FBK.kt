package com.mtsa.resistcalc.fbk

import com.mtsa.utils.Utils
import java.io.Serializable

class FBK (// Values as Strings
    val amostras: FloatArray
) : Serializable {
    
    val lista = amostras
    // armazena a quantidade de amostras em “n”
    val n = amostras.count()
    // determina o valor de “i” dependendo da quantidade de amostras
    // n/2, se for par;
    // (n-1)/2, se for ímpar;
    var i = if (n % 2 == 0) n / 2 else (n - 1) / 2
    
    init {
        // como todos os valores utilizam o i-1, é mais prático já decremenatr agora
        i--
    }
    
    // cria uma sublista do menor elemento da amostra, até a posição de "i" e soma todos esses valores
    val soma = amostras.toList().subList(0, i).sum()
    // "fbi" será o elemento na posição "i"
    val fbi = amostras[i]
    
    // nesta linha há o emprego da equação para obtenção da resistência à compressão estimada em blocos cerâmicos
    val fbk = ((2 * soma) / i) - fbi
    
    // o "fbm" é a média da resistência à compressão de todos os corpos-de-prova da amostra
    val fbm = amostras.sum() / n
    
    // checa os valores de Ø em função da quantidade de blocos da amostra
    val tabela = arrayOf(
//        3.0777F,
//        1.8856F,
//        1.8856F,
//        1.5332F,
//        1.4759F,
//        1.4398F,
//        1.4149F,
//        1.3968F,
//        1.3830F,
//        1.3722F,
//        1.3634F,
//        1.3562F,
//        1.3502F,
//        1.3450F,
//        1.3406F,
//        1.3368F,
//        1.3334F,
//        1.3304F,
//        1.3277F,
//        1.3253F

        0.89F,
        0.89F,
        0.89F,
        0.89F,
        0.89F,
        0.89F,
        0.91F,
        0.93F,
        0.94F,
        0.96F,
        0.97F,
        0.98F,
        0.99F,
        1F,
        1.01F,
        1.02F,
        1.02F,
        1.04F
    )
    val menorLimite = if (n < 18)
        fbk * tabela[n-1]
    else
        fbk * tabela.lastIndex
    
    // verifica os requisítos para finalmente obter a resistência característica do lote (fbk)
    val resistencia = when {
        fbk >= fbm -> fbm
        fbk < menorLimite -> menorLimite
        else -> fbk
    }

    val sLista = lista.contentToString()
        .replace(", ", "\t\t")
        .replace("[","")
        .replace("]","")
    val sN = n.toString()
    val sI = (i+1).toString()
    val sSoma = Utils.twoDec(soma)
    val sFbi = Utils.twoDec(fbi)
    val sFbk = Utils.twoDec(fbk)
    val sFbm = Utils.twoDec(fbm)
    val sMenorLimite = Utils.twoDec(menorLimite)
    val sResistencia = Utils.twoDec(resistencia)
}