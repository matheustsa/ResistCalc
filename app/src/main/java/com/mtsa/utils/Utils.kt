package com.mtsa.utils

import android.content.Context
import androidx.core.content.ContextCompat
import com.mtsa.resistcalc.R
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import kotlin.coroutines.coroutineContext
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

    /*
    /**
     * Thread Safe Active
     * to remove it just delete "(LazyThreadSafetyMode.NONE)"
     */
    fun <T : View> Activity.bind(@IdRes res : Int) : Lazy<T> {
        @Suppress("UNCHECKED_CAST")
        return lazy(LazyThreadSafetyMode.NONE){ findViewById(res) }
    }
    /**
     * For custom Views
     */
    fun <T : View> View.bind(@IdRes res : Int) : Lazy<T> {
        @Suppress("UNCHECKED_CAST")
        return lazy(LazyThreadSafetyMode.NONE){ findViewById(res) }
    }
     */
}