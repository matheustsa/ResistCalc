package com.mtsa.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import kotlin.math.pow
import kotlin.math.sqrt

object Utils {
    /**
     * Convert received value to only two decimals
     *
     * @return String ou Double (arredondado em duas casas decimais)
     * @param number - Float to be converted
     */
    fun twoDec(number: Float): String? {
        val df = DecimalFormat("#.##")
        return df.format(number)
    }
    fun twoDec(number: Double): String? {
        val df = DecimalFormat("#.##")
        return df.format(number)
    }

    fun roundDec(number: Double, decimals: Int): Double {
        var pattern = "#."

        if (decimals <= 1)
            throw CustomException("O número de casas decimais precisa ser maior que 1")
        else
            for (i in 1..decimals)
                pattern += "0"

        val df = DecimalFormat(pattern)

        // To prevet locale errors with "," and "."
        val dfs = DecimalFormatSymbols();
        dfs.decimalSeparator = '.';
        df.decimalFormatSymbols = dfs

        return df.format(number).toDouble()
    }

    fun roundString(number: Double, decimals: Int): String {
        var pattern = "#."

        if (decimals <= 1)
            throw CustomException("O número de casas decimais precisa ser maior que 1")
        else
            for (i in 1..decimals)
                pattern += "0"

        val df = DecimalFormat(pattern)
        return df.format(number)
    }


    /**
     * Calcula a variância da amostra, que consiste na diferença entre as
     * resistências e a média (n-1), elevando ao quadrado cada elemento.
     *
     * @return Double (arredondado em duas casas decimais)
     * @param amostras - FloatArray com as amostras coletadas
     */
    fun variancia(amostras: FloatArray): Double {
        val media = amostras.average()
        var variancia = 0.0
        for (i in amostras.indices)
            variancia += (amostras[i] - media).pow(2.0).toFloat()
        variancia /= amostras.count() - 1

        return roundDec(variancia, 4)!!
    }
    fun variancia(amostras: List<Double>): Double {
        val media = amostras.average()
        var variancia = 0.0
        for (i in amostras.indices)
            variancia += (amostras[i] - media).pow(2.0).toFloat()
        variancia /= amostras.count() - 1

        return roundDec(variancia, 4)!!
    }

    /**
     * Calcula o Desvio Padrão da amostra, retornando a raiz quadrada da variância
     *
     * @param amostras - FloatArray com as amostras coletadas
     * @return Double (arredondado em duas casas decimais)
     */
    fun desvioPadrao(amostras: FloatArray): Double {
        return roundDec(sqrt(variancia(amostras)), 4)!!
    }
    fun desvioPadrao(amostras: List<Double>): Double {
        return roundDec(sqrt(variancia(amostras)), 4)!!
    }

    /**
     * Calcula o Coeficiente de Variação da amostra
     *
     * @param media - Média da amostra
     * @param desvioPadrao - Desvio Padrão da amostra
     * @return Double (arredondado em duas casas decimais)
     */
    fun coefVariacao(media: Double, desvioPadrao: Double): Double {
        return roundDec((desvioPadrao/media)*100, 4)!!
    }

    /**
     * Lança uma Exception com a mensagem passada no parâmetro
     *
     * @param message - Mensagem de erro que será informada
     */
    class CustomException(message: String) : Exception(message)

    fun listaExemplo(op: String): List<List<Double>> {

        // TODO: 21/06/2020 CONERTAR ISSO DENTRO DA ENTRADA
        when (op) {
//            "fbk" -> return "12,4; 12,4; 12,4; 12,6; 13,5; 13,5; 13,9; 14,2; 14,2; 15,1; 15,4; 16,2; 16,4; 17,4"
//            "fpk" -> return "12,4; 12,4; 12,6; 13,5; 15,4; 16,4; 17,4"

            "dic" -> return listOf(
                listOf(20.4, 22.6, 23.4, 24.6, 22.4, 22.6, 34.6, 25.6, 26.1, 29.2),
                listOf(18.6, 18.9, 19.6, 19.2, 20.4, 24.6, 23.1, 22.1, 18.5, 19.1),
                listOf(18.6, 18.9, 19.6, 22.4, 22.6, 26.4, 27.4, 26.4, 26.4, 22.4),
                listOf(17.5, 18.5, 16.2, 14.3, 18.9, 19.6, 14.6, 22.5, 21.3, 19.5),
                listOf(21.4, 21.8, 22.6, 22.4, 22.6, 34.6, 26.8, 24.6, 24.6, 24.5),
                listOf(22.4, 22.6, 34.6, 24.6, 22.6, 23.6, 18.6, 18.9, 19.6, 24.6)
            )

            else -> throw CustomException("OPERAÇÃO NÃO RECONHECIDA")
        }
    }
}