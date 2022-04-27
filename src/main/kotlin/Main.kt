import util.StockUtil

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
    val allStocks = StockUtil.readStocksFromFile(ALL_STOCKS_FILE);
    val caredStocks = StockUtil.filterCaredStocks(allStocks);
    StockUtil.setStocksIndustry(caredStocks)
    if (caredStocks != null) {
        StockUtil.saveStocksToFile(caredStocks, "cared-stocks.json")
    }
}