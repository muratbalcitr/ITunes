package com.m2lifeApps.hepsiburada.core.extensions

fun Double.toCurrency(code: String): String {
    return "$this $code"
}
