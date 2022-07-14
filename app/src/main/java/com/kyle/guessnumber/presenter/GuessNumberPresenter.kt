package com.kyle.guessnumber.presenter

import android.view.View
import com.kyle.guessnumber.Constants
import com.kyle.guessnumber.model.GuessNumberModel
import com.kyle.guessnumber.view.GuessNumberViewActivity
import kotlin.collections.HashMap


/*
 *@author Kyle Lin
 *@data 2022/5/3
 */
class GuessNumberPresenter : GuessNumberPresenterImpl, GuessNumberListener {
     var mainViewActivity: GuessNumberViewActivity?=null
     var mainModel: GuessNumberModel?=null
     var guessTime = 1
     var finalResult = false

     constructor(mainViewActivity: GuessNumberViewActivity){
         this.mainViewActivity  = mainViewActivity
         this.mainModel = GuessNumberModel(this)

         mainViewActivity.initView()
         mainViewActivity?.showDialog("遊戲開始","請點選開始遊戲","開始遊戲","進入設置",2)
     }

    override fun initGame() {
        mainModel?.answer = mainModel!!.createAnswer()
        clearAllNumber(null)
    }

    override fun inputNumber(view: View?) {
        /* 輸入限制 必須最多四個 , 輸入後直接比對所有的 button id ,
         * 定位倒是哪個 button id 後 . 將 int 轉為 string
         * 並將字串串接起來 , 用於顯示當前輸入
         */
        if (mainViewActivity?.inputResult?.length!! < 4) {
            for (i in mainViewActivity?.btnNumber!!.indices) {
                if (view === mainViewActivity!!.btnView[i]) {
                    mainViewActivity!!.btnView[i]!!.isEnabled = false
                    mainViewActivity!!.btnView[i]!!.text = "X"
                    mainViewActivity?.inputResult += i.toString()
                    break
                }
            }
            mainViewActivity?.setInputText(mainViewActivity!!.inputResult)
        } else {
            mainViewActivity!!.toMessage("已輸入達上限")
        }
    }

    override fun deleteNumber(view: View?) {
        if (mainViewActivity!!.inputResult.isNotEmpty() && mainViewActivity?.inputResult?.length != 0) {
            var myString = StringBuilder(mainViewActivity?.inputResult)
            /*
             * 查出 String 最後一個字 , 比對 button 號碼 , 重新啟用該button
             */
            val re_enable = mainViewActivity!!.inputResult[mainViewActivity!!.inputResult.length - 1]
            for (i in mainViewActivity!!.btnNumber.indices) {
                if (Character.getNumericValue(re_enable) == i)
                    mainViewActivity?.setButtonEnable(i,true)
            }

            /* 刪除最後一次的輸入 , 並將可輸入限制減一 , TextView 重新顯示 */
            myString =  myString.deleteCharAt(mainViewActivity!!.inputResult.length - 1)
            mainViewActivity?.inputResult = myString.toString()
            mainViewActivity?.setInputText(mainViewActivity!!.inputResult)
        }
    }

    override fun clearAllNumber(view: View?) {
        /* 清除所有設置 , 還原按鍵可按 */
        mainViewActivity?.inputResult = ""
        mainViewActivity?.setInputText(mainViewActivity!!.inputResult)
        for (i in mainViewActivity!!.btnNumber.indices) {
            mainViewActivity?.setButtonEnable(i,true)
        }
    }

    override fun sendResultNumber(view: View?) {
        /* 確認輸入的字串是否有四個後 , 傳入輸入號碼 , 讓 model 去比對 */
        if (mainViewActivity?.inputResult?.length != 4) return

        mainModel?.compareNumber(mainViewActivity!!.inputResult)

    }

    override fun replay(view: View?) {
        initGame()
        mainViewActivity?.itemsNumber!!.clear()
        mainViewActivity?.adapter!!.notifyDataSetChanged()
        mainViewActivity?.resetTime()
    }

    override fun updateView(compareResult: HashMap<String, String>) {
        /* 第一組 是代表第幾次
         * 第二組 是代表本次輸入
         * 第三組 是跟 answer 比對的結果
         * 將 這個 SimpleAdapter 中已定義的第二組參數 items_number
         * 最後 update SimpleAdapter , 並設定 ListView 會顯示到最後一組
         */
        val resultA = compareResult?.get(mainModel?.keyA).toString()+"A"
        val resultB = compareResult?.get(mainModel?.keyB).toString()+"B"
        if(resultA == "4A"){
            finalResult = true
        }
        val row = HashMap<String, String>()
        row[mainViewActivity!!.from[0]] = "" + (mainViewActivity?.itemsNumber!!.size + 1)
        row[mainViewActivity!!.from[1]] = mainViewActivity!!.inputResult
        row[mainViewActivity!!.from[2]] = (resultA + resultB)
        mainViewActivity?.itemsNumber!!.add(row)
        mainViewActivity?.adapter!!.notifyDataSetChanged()
        mainViewActivity?.listView!!.smoothScrollToPosition(mainViewActivity?.itemsNumber!!.size - 1)
        clearAllNumber(null)
        if(guessTime<Constants.gameSettings && !finalResult){
            guessTime++
        }else{
            if (finalResult){
                mainViewActivity?.showDialog("結果顯示","答案正確 , 遊戲結束","重新開始",null,1)
            }else{
                mainViewActivity?.showDialog("結果顯示","已猜錯 $guessTime 次遊戲結束","重新開始",null,1)
            }
            mainViewActivity?.mTimeUpdate?.let { mainViewActivity?.mHandler?.removeCallbacks(it) }
            guessTime = 1
        }
    }




}