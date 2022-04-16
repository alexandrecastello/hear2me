package com.lft.hear4me

import android.R
import android.media.MediaPlayer


fun main(){

    val str = "A:B:C"
    val delim = ":"

    val list:List<String> = str.split(delim)

    println(list[list.lastIndex])    // [A, B, C]

}