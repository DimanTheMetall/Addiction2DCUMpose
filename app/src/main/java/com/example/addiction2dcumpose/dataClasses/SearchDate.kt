package com.example.addiction2dcumpose.dataClasses

class SearchDate(val year: Int, val month: Int?, val day: Int?) {

    fun getDate(): String {
        var result = "$year"
        if (month != null) {
            result = "$month-$year"
            if (day != null) {
                result = "$month-$day-$year"
            }
        }
        return result
    }
}