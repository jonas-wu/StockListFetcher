package util

import java.util.regex.Pattern

object StockConst {
    const val MAX_STOCK_PB = 1.5f
    const val MAX_STOCK_PE = 20f
    const val MAX_STOCK_TRADE_VALUE = 100f
    const val MAX_STOCK_TOTAL_VALUE = 150f

    val PATTEN_STOCK_INDUSTRY = Pattern.compile(">所属行业：(.*)</")
    val PATTEN_STOCK_CONCEPT = Pattern.compile(">所属概念：(.*)</")
}