package util

object Log {
    const val DEBUG = false

    fun v(s: String) {
        if (DEBUG) {
            println(s)
        }
    }

    fun d(s: String) {
        println(s)
    }

    fun e(s: String) {
        println(s)
    }

    fun e(e: Exception) {
        println(e)
    }
}