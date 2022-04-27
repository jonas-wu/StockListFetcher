package util

object Log {
    fun v(s: String) {
        println(s)
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