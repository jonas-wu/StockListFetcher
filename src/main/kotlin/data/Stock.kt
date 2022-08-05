package data

import util.StockConst

/**
 * http://qt.gtimg.cn/q=s_sh600519
 * v_sz001209="51~洪兴股份~001209~22.92~23.86~22.66~29820~11161~18658~22.91~27~22.90~353~22.88~16~22.87~19~22.86~24~22.92~34~22.94~14~22.96~6~22.98~1~22.99~2~~20220425161403~-0.94~-3.94~23.78~22.56~22.92/29820/68600910~29820~6860~12.70~17.06~~23.78~22.56~5.11~5.38~21.53~1.77~26.25~21.47~3.41~382~23.01~22.29~16.10~~~0.82~6860.0910~0.0000~0~ ~GP-A~-15.98~6.41~2.18~9.97~8.40~46.83~21.01~5.14~-0.61~-14.16~23486499~93944805~77.02~-51.06~23486499~";
 */
data class Stock(
// 2
    val code: String,

// 1
    var name: String,

// 30
    var time: String = "",

// 39
    var pe: Float = 0f,

// 46
    var pb: Float = 0f,

// 44
    var tradeValue: Float = 0f,

// 45
    var totalValue: Float = 0f,
    var industry: String = "",
    var concept: String = "",
    var priceDay: String = "",
    var prices: Array<StockPrice>? = null,
) {
    constructor(code: String) : this(code, "")

    override fun toString(): String {
        return priceDay.substring(5) +
                " " + code +
                " " + name +
//                " " + time.substring(4, 8) +
                "\tPE=\t" + pe +
                "\tPB=\t" + pb +
                "\t" + tradeValue +
                "\t" + totalValue +
                "\t" + industry + concept
    }

    fun toShortString(): String {
        return "Stock{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}'
    }

    fun needCare(): Boolean {
        return pb < StockConst.MAX_STOCK_PB
                && pe > 0 && pe < StockConst.MAX_STOCK_PE
                && tradeValue < StockConst.MAX_STOCK_TRADE_VALUE
                && totalValue > StockConst.MIN_STOCK_TOTAL_VALUE
                && totalValue < StockConst.MAX_STOCK_TOTAL_VALUE
                && !name.contains("ST")
    }
}

val STOCK_COMPARATOR = java.util.Comparator { a: Stock, b: Stock ->
    if (a.industry == null) {
        if (b.industry == null) {
            return@Comparator a.tradeValue.compareTo(b.tradeValue)
        } else {
            return@Comparator 1
        }
    }
    val ret = a.industry.compareTo(b.industry)
    if (ret == 0)
        a.pe.compareTo(b.pe)
    else
        ret
}