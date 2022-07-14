package com.kyle.guessnumber.model

import java.util.*
import kotlin.collections.HashMap

/*
 *@author Kyle Lin
 *@data 2022/5/4
 */
interface GuessNumberModelImpl {
    fun createAnswer(): LinkedList<Int>
    fun compareNumber(inputResult:String)
}