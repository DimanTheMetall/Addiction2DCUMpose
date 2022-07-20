package com.example.addiction2dcumpose.dataClasses

class SearchDate(private val year: Int, private val month: Int?, private val day: Int?) {

    fun getDate(): String{
        var result = "$year"
        if (month!=null){
            result+= "-$month"
            if (day!=null) {
                result+="-$day"
            }
        }
        return result
    }
}