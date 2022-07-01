package ru.netology.nmedia

import android.view.View
import java.text.DecimalFormat
import kotlin.math.round

fun formatCount(count: Long): String { // метод реализующий конвертацию числа
    val df = DecimalFormat("#.#")
    when (count) {
        in 0..999 -> return count.toString() // до 999
        in 1000..999_99 -> return "${round(count.toDouble() / 1000).toLong()}K" // до 99К
        in 100_000..999_999 -> return "${(count.toDouble() / 1000).toLong()}K" // до 999К
        in 1_000_000..9_000_000 -> return "${
            df.format(count.toDouble() / 1000000).toString().toLong()
        }M"
        else -> return "${round(count.toDouble() / 1000000).toLong()}M"
    }
}
