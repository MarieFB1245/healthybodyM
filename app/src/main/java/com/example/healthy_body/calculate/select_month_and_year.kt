package com.example.healthy_body.calculate

import java.time.Year

class select_month_and_year (val year :Int ,val month  :Int){


    fun selectdate(callback: (YEAR:Int,MONTH:String) -> Unit){


        var yearTh = year + 543


        if(month == 1){
           val MONTH = "มกราคม"
            callback(yearTh,MONTH)
        }else if (month == 2){
            val MONTH = "กุมภาพันธ์"
            callback(yearTh,MONTH)
        }else if (month == 3){
            val MONTH = "มีนาคม"
            callback(yearTh,MONTH)
        }else if (month == 4){
            val MONTH = "เมษายน"
            callback(yearTh,MONTH)
        }else if (month == 5){
            val MONTH = "พฤษภาคม"
            callback(yearTh,MONTH)
        }else if (month == 6){
            val MONTH = "มิุนายน"
            callback(yearTh,MONTH)
        }else if (month == 7){
            val MONTH = "กรกฎาคม"
            callback(yearTh,MONTH)
        }else if (month == 8){
            val MONTH = "สิงหาคม"
            callback(yearTh,MONTH)
        }else if (month == 9){
            val MONTH = "กันยายน"
            callback(yearTh,MONTH)
        }else if (month == 10){
            val MONTH = "ตุลาคม"
            callback(yearTh,MONTH)
        }else if (month == 11){
            val MONTH = "พฤศจิกายน"
            callback(yearTh,MONTH)
        }else{
            val MONTH = "ธันวาคม"
            callback(yearTh,MONTH)
        }
    }


}