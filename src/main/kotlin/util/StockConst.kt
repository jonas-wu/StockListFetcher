package util

import java.util.regex.Pattern

object StockConst {
    const val MAX_STOCK_PB = 2.0f
    const val MAX_STOCK_PE = 45f
    const val MAX_STOCK_TRADE_VALUE = 300f
    const val MIN_STOCK_TOTAL_VALUE = 30f
    const val MAX_STOCK_TOTAL_VALUE = 350f

    val PATTEN_STOCK_INDUSTRY = Pattern.compile(">所属行业：(.*)</")
    val PATTEN_STOCK_CONCEPT = Pattern.compile(">所属概念：(.*)</")
}