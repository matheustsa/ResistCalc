package com.mtsa.utils

import java.text.DecimalFormat

object Utils {
    /**
     * Convert received value to String with only two decimals
     *
     * @param number - Float to be converted
     */
    fun twoDec(number: Float): String? {
        val df = DecimalFormat("#.##")
        return df.format(number)
    }
}