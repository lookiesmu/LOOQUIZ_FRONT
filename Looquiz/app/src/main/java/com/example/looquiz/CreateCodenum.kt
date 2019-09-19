package com.example.looquiz

import java.util.*

class CreateCodenum{
    val pwlength:Int = 6
    val char:Array<Char> = arrayOf(
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
        'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
        'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
        'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
        'w', 'x', 'y', 'z', '!', '@', '#', '$', '%', '^', '&', '*',
        '(', ')', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' )

    fun execute() : String{
        var result : String = ""

        val random : Random =  Random()
        for(i in 0 until pwlength)
            result += char[random.nextInt(char.size)]

        return result
    }
}