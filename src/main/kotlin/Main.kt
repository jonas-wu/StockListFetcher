import data.STOCK_COMPARATOR
import util.StockUtil
import util.Urls

const val ALL_STOCKS_FILE = "all-stocks.json"

fun main(args: Array<String>) {
    println("main start")

    fetchCaredStocks()


    println("main end")
}

fun fetchAllStocks() {
    val stocks = StockUtil.fetchAllStockByWebQt();
    StockUtil.saveStocksToFile(stocks, ALL_STOCKS_FILE)
}

fun fetchCaredStocks() {
    val allStocks = StockUtil.readStocksFromFile(ALL_STOCKS_FILE)
//    val allStocks = StockUtil.readStocksFromFile(ALL_STOCKS_FILE)!!.subList(0, 500)
    val caredStocks = StockUtil.filterCaredStocks(allStocks);
    StockUtil.setStocksIndustry(caredStocks)
    if (caredStocks != null) {
        caredStocks.sortWith(STOCK_COMPARATOR)
        StockUtil.saveStocksToFile(caredStocks, "cared-stocks.json")
    }
}

fun testFetchPrices(): Array<StockPrice>? {
    val html = StockUtil.fetchHtml(String.format(Urls.STOCK_DAY_PRICES_URL_QT, StockUtil.getFullCode("000498")))
    val prices = StockUtil.parseStockPricesQt(html)
    return prices
}

val testPrices = arrayOf(
    StockPrice("2022-03-24", 8.03f, 8.43f, 7.89f, 8.35f, 25),
    StockPrice("2022-03-25", 8.03f, 8.23f, 7.94f, 8.04f, 165),
    StockPrice("2022-03-28", 8f, 8.43f, 7.89f, 8.35f, 250),
    StockPrice("2022-03-29", 8.41f, 8.57f, 8.19f, 8.57f, 322),
    StockPrice("2022-03-30", 8.5f, 8.79f, 8.42f, 8.69f, 335),
)

private fun isContinueRising(prices: Array<StockPrice>): Boolean {
    val one = prices[STOCK_PRICE_LEN - 1]
    val two = prices[STOCK_PRICE_LEN - 2]
    val three = prices[STOCK_PRICE_LEN - 3]
    val four = prices[STOCK_PRICE_LEN - 4]

    val percent = (one.close - two.close) / two.close * 100
    val dayPercent = (one.close - one.open) / one.open * 100
    val volumePercent = (one.volume - two.volume) * 100f / two.volume

    val percent2 = (two.close - three.close) / three.close * 100
    val dayPercent2 = (two.close - two.open) / two.open * 100
    val volumePercent2 = (two.volume - three.volume) * 100f / three.volume

    val percent3 = (three.close - four.close) / four.close * 100
    val dayPercent3 = (three.close - three.open) / three.open * 100
    val volumePercent3 = (three.volume - four.volume) * 100f / four.volume

    val ret = dayPercent > 0.01f && percent < 5f
            && dayPercent2 > 0.01f && percent2 < 5f
            && dayPercent3 > 0.01f && percent3 < 5f
            && one.close > four.close
    if (ret) {
        println("percent=$percent dayPercent=$dayPercent")
        println("percent2=$percent2 dayPercent2=$dayPercent2")
        println("percent3=$percent3 dayPercent3=$dayPercent3")
        println("volumePercent=$volumePercent volumePercent2=$volumePercent2 volumePercent3=$volumePercent3")
    }
    return ret
}

data class StockPrice(
    var day: String = "",
    var open: Float = 0f,
    var high: Float = 0f,
    var low: Float = 0f,
    var close: Float = 0f,
    var volume: Int = 0
)

const val STOCK_PRICE_LEN = 5
