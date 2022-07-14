package com.kyle.guessnumber.presenter

import android.view.View

/*
 *@author Kyle Lin
 *@data 2022/5/3
 */
interface GuessNumberPresenterImpl {
    fun initGame()
    fun inputNumber(view: View?)
    fun deleteNumber(view: View?)
    fun clearAllNumber(view: View?)
    fun sendResultNumber(view: View?)
    fun replay(view: View?)
}