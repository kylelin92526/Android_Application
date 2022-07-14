package com.kyle.guessnumber.model

import com.kyle.guessnumber.presenter.GuessNumberListener
import java.util.*
import kotlin.collections.HashMap


/*
 *@author Kyle Lin
 *@data 2022/5/3
 */
class GuessNumberModel : GuessNumberModelImpl {
    var answer = LinkedList<Int>()
    var keyA = "keyA"
    var keyB = "keyB"
    var mListener: GuessNumberListener?=null

    constructor(guessNumberListener: GuessNumberListener){
        mListener = guessNumberListener
    }

    override fun createAnswer(): LinkedList<Int> {
        val ret = LinkedList<Int>()
        val nums = HashSet<Int>()
        while (nums.size < 4) {
            nums.add((Math.random() * 10).toInt())
        }
        for (i in nums) {
            ret.add(i)
        }

        /* 主要作法是將 HashSet 不重複的集合 , 導入 LinkList 後
         * 用 Collections.shuffle 的方法重新排序L List
         */
        ret.shuffle()
        return ret
    }

    override fun compareNumber(inputResult:String) {
        /* 將 輸入的 input string 轉 char array 後 ,
         * 代入 LinkList 中 , 接著開始比對
         * 首先計算位置跟 answer 是否相同 ,
         * 再確認是否有 answer 中有包含
         * 接著把結果帶入  HashMap 中 { key , value }
         * Listener callback 方式 回傳回 GuessNumberPresenter 去更新 view
         */
        var valueA = 0;
        var valueB = 0;
        val inputValue = LinkedList<Int>()
        val inputChar = inputResult.toCharArray()
        for (i in inputChar.indices) {
            inputValue.add(inputChar[i].toString().toInt())
        }
        for (i in inputValue.indices) {
            if (answer[i] === inputValue[i]) {
                valueA++
            } else if (answer.contains(inputValue[i])) {
                valueB++
            }
        }
        val result = HashMap<String,String>()
        result[keyA] = valueA.toString()
        result[keyB] = valueB.toString()
        mListener?.updateView(result)
    }
}