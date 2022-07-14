package com.kyle.guessnumber.view

/*
 *@author Kyle Lin
 *@data 2022/5/3
 */
interface GuessNumberViewImpl{
    fun initView()
    fun setInputText(str:String)
    fun setButtonEnable(num:Int,enable:Boolean)
    fun showDialog(title:String,message:String,btnName:String,btnName2:String?,style:Int)
    fun toMessage(msg: String)
    fun setTitleText(str:String)
    fun resetTime()
}