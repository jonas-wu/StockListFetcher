package data

data class StockPrice(
    var day: String = "",
    var open: Float = 0f,
    var high: Float = 0f,
    var low: Float = 0f,
    var close: Float = 0f,
    var volume: Int = 0
)