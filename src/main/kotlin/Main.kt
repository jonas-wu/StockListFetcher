import data.STOCK_COMPARATOR
import data.Stock
import util.Log
import util.StockUtil
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

const val ALL_STOCKS_FILE = "all-stocks.json"
const val CARED_STOCKS_FILE = "cared-stocks.json"

fun main(args: Array<String>) {
    println("main start")
    printLatestPrice()

//    fetchAllStocks()
//    fetchCaredStocks()

    val stocks_1 = mutableListOf<Stock>()
    val stocks_2 = mutableListOf<Stock>()
    findCaredStocks(stocks_1, stocks_2)

    if (stocks_1.isNotEmpty()) {
        writeStocksToFile(stocks_1, "stocks1.txt")
    }
    if (stocks_2.isNotEmpty()) {
        writeStocksToFile(stocks_2, "stocks2.txt")
    }

    println("main end")
}

fun writeStocksToFile(stocks: List<Stock>, fileName: String) {
    val sb = StringBuilder()
    stocks.map {
        sb.append(it.toString()).append("\r\n")
    }
    val path = Paths.get(fileName)
    Files.deleteIfExists(path)
    Files.writeString(path, sb.toString(), StandardOpenOption.CREATE, StandardOpenOption.WRITE)
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

fun findCaredStocks(stocks_1: MutableList<Stock>, stocks_2: MutableList<Stock>) {
    stocks_1.clear()
    stocks_2.clear()

    val stocks = StockUtil.readStocksFromFile(CARED_STOCKS_FILE)!!
    val total = stocks.size
//    val total = 100

    val riseAndFallStocks = mutableListOf<Stock>()
    val riseLimitStocks = mutableListOf<Stock>()

    Log.d("\ncheck riseAndFallStocks\n")
    for (i in 0 until total) {
        val stock = stocks[i]
        val ret = StockUtil.isFirstRaiseLimitAndFall(stock)
        if (ret) {
            Log.d("${i}/${total} ${stock.name}")
            riseAndFallStocks.add(stock)
//            break
        }
    }

    Log.d("\ncheck riseLimitStocks\n")
    for (i in 0 until total) {
        val stock = stocks[i]
        if (riseAndFallStocks.contains(stock)) {
            continue
        }
        val ret = StockUtil.isFirstRaiseLimit(stock)
        if (ret) {
            Log.d("${i}/${total} ${stock.name}")
            riseLimitStocks.add(stock)
//            break
        }
    }

    riseAndFallStocks.sortWith(STOCK_COMPARATOR)
    riseLimitStocks.sortWith(STOCK_COMPARATOR)

    stocks_1.addAll(riseAndFallStocks)
    stocks_2.addAll(riseLimitStocks)
}

fun printLatestPrice() {
    val prices = StockUtil.fetchPricesByWebQt("601218", 1)
    if (!prices.isNullOrEmpty()) {
        Log.d("Latest price day: ${prices[0].day}")
    }
}
