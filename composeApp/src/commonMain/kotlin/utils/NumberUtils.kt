package utils

fun String.trimTrailingZerosIfNumber(): String {
    runCatching {
        val doubleValue = toDouble()
        if (doubleValue % 1.0 == 0.0) {
            doubleValue.toInt().toString()
        } else {
            this.trimEnd('0').trimEnd('.')
        }
    }.onSuccess { number ->
        return number
    }
    return this
}