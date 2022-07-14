package com.kyle.guessnumber.presenter

/*
 *@author Kyle Lin
 *@data 2022/5/5
 */
interface GuessNumberListener {
    fun updateView(compareResult:HashMap<String, String>)
}