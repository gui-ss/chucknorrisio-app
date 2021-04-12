package com.stefanini.chucknorrisio

import android.graphics.Color
import java.util.*

class Colors {

    companion object{
        @JvmStatic
        fun randomColor(): Int{
            val random: Random = Random()
            val r : Int = random.nextInt(0xFF)
            val g : Int = random.nextInt(0xFF)
            val b : Int = random.nextInt(0xFF)

            return Color.argb(0X80, r, g, b)
        }
    }
}