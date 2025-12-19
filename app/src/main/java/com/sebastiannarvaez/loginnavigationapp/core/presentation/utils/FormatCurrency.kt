package com.sebastiannarvaez.loginnavigationapp.core.presentation.utils

import java.text.NumberFormat
import java.util.Locale

fun Double.toCurrency(): String {
    val format = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
    return format.format(this)
}