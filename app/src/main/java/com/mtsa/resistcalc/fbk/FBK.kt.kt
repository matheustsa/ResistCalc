package com.mtsa.resistcalc.fbk

import com.mtsa.utils.Utils
import java.io.Serializable

class FBK (amostras: FloatArray) : Serializable {
    
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
        amostras[0] * tabela[n - 1]
    else
        amostras[0] * tabela.lastIndex
    
    // verifica os requisítos para finalmente obter a resistência característica do lote (fbk)
    val resistencia = when {
        fbk >= fbm -> fbm
        fbk < menorLimite -> menorLimite
        else -> fbk
    }
    
    // Values as Strings
    val sLista = lista.contentToString()
    val sN = n.toString()
    val sI = (i+1).toString()
    val sSoma = Utils.twoDec(soma)
    val sFbi = Utils.twoDec(fbi)
    val sFbk = Utils.twoDec(fbk)
    val sFbm = Utils.twoDec(fbm)
    val sMenorLimite = Utils.twoDec(menorLimite)
    val sResistencia = Utils.twoDec(resistencia)
}