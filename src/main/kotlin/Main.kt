import data.STOCK_COMPARATOR
import util.Log
import util.StockUtil

const val ALL_STOCKS_FILE = "all-stocks.json"
const val CARED_STOCKS_FILE = "cared-stocks.json"

fun main(args: Array<String>) {
    println("main start")

//    fetchAllStocks()
//    fetchCaredStocks()

//    findFirstRaisingLimitStocks()
    printLatestPrice()
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

fun findFirstRaisingLimitStocks() {
    val stocks = StockUtil.readStocksFromFile(CARED_STOCKS_FILE)!!
    val total = stocks.size
    for (i in 0 until total) {
        val stock = stocks[i]
//        Log.d("${i}/${total} ${stock.toShortString()}")
        val ret = StockUtil.isFirstRaisingLimit(stock)
        if (ret) {
//            Log.d("${i}/${total} ${stock}")
            Log.d("$stock")
//            break
        }
    }
}

fun printLatestPrice() {
    val prices = StockUtil.fetchPricesByWebQt("601218", 1)
    if (!prices.isNullOrEmpty()) {
        Log.d(prices[0].day)
    }
}
