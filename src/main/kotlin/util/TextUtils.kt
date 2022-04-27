package util

object TextUtils {
    fun isEmpty(s: String?): Boolean {
        return s == null || s.isEmpty()
    }
}