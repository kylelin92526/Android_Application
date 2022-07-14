package com.kyle.guessnumber.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.kyle.guessnumber.Constants
import com.kyle.guessnumber.presenter.GuessNumberPresenter
import com.kyle.guessnumber.R
import com.kyle.guessnumber.SettingsActivity
import java.util.*


class GuessNumberViewActivity : AppCompatActivity(), GuessNumberViewImpl {

    var mGuessNumberPresenter: GuessNumberPresenter?=null
    var listView: ListView?=null
    var adapter: SimpleAdapter?=null
    val from = arrayOf("times", "input_number", "compare_result")
    private val to: IntArray = intArrayOf(R.id.times, R.id.input_number, R.id.compare_result)
    var itemsNumber: LinkedList<HashMap<String, String>>?=null
    var showInputNumber: TextView? = null
    val btnNumber = arrayOf(
        R.id.button0,
        R.id.button1,
        R.id.button2,
        R.id.button3,
        R.id.button4,
        R.id.button5,
        R.id.button6,
        R.id.button7,
        R.id.button8,
        R.id.button9
    )
    //val btnView = arrayOfNulls<View>(10)
    val btnView = arrayOfNulls<Button>(10)
    var inputResult = ""
    var titleView:TextView?=null
    var mHandler = Handler()
    var startTime: Long? = null
    var dialog:AlertDialog?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        if(mGuessNumberPresenter!=null){
            showDialog("遊戲開始","請點選開始遊戲","開始遊戲","進入設置",2)
        }else{
            mGuessNumberPresenter = GuessNumberPresenter(this@GuessNumberViewActivity)
        }
    }

    override fun onPause() {
        super.onPause()
        /*
          對話框在 HOME 跳出時 必須關閉
          否則再次進入會出現重複再跳出一次對話框問題
         */
        dialog?.dismiss()
        mHandler.removeCallbacks(mTimeUpdate)
    }

    override fun initView() {
        setContentView(R.layout.activity_guess_number)
        listView = findViewById(R.id.guess_number_listview)
        itemsNumber = LinkedList()
        adapter = SimpleAdapter(this, itemsNumber, R.layout.item_guess_number_list, from, to)
        listView?.adapter = adapter
        showInputNumber = findViewById(R.id.textViewshow)
        titleView = findViewById(R.id.textTitle)
        var j = 0
        for (i in btnNumber) {
            btnView[j] = findViewById(i)
            if (j < btnView.size) j++
        }
    }

    fun settingsOnClick(view: View?) {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
        Toast.makeText(this,"停止遊戲 ,  進入設置介面",Toast.LENGTH_SHORT).show()
    }

    override fun setInputText(str:String) {
        showInputNumber?.text = str
    }

    override fun setButtonEnable(num: Int, enable: Boolean) {
        btnView[num]?.isEnabled = enable
        btnView[num]?.text = num.toString()
    }

    fun input(view: View?) {
        mGuessNumberPresenter?.inputNumber(view)
    }

    fun delete(view: View?) {
        mGuessNumberPresenter?.deleteNumber(view)
    }

    fun clearAll(view: View?) {
        mGuessNumberPresenter?.clearAllNumber(view)
    }

    fun sendResult(view: View?) {
        mGuessNumberPresenter?.sendResultNumber(view)
    }

    fun replayGame(view: View?) {
        mGuessNumberPresenter?.replay(view)
    }

    override fun showDialog(title:String,message:String,btnName:String,btnName2:String?,style:Int) {
        var builder=AlertDialog.Builder(this)
        builder!!.setTitle(title)
        builder!!.setMessage(message)
        builder!!.setCancelable(false)
        builder!!.setPositiveButton(btnName) { _, _
                    ->  mGuessNumberPresenter?.replay(null)
                 }
        if(style==2){
            builder!!.setNegativeButton(btnName2){_, _
                ->  settingsOnClick(null)
            }
        }
        dialog = builder?.create()
        dialog?.show()
    }

    override fun toMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun setTitleText(str: String) {
        titleView?.text = str
    }

    override fun resetTime() {
        startTime = System.currentTimeMillis()
        mHandler.removeCallbacks(mTimeUpdate)
        mHandler.post(mTimeUpdate)
    }

    val mTimeUpdate: Runnable = object : Runnable {
        override fun run() {
            val spentTime = System.currentTimeMillis() - startTime!!
            val minius = spentTime / 1000 / 60
            val seconds = spentTime / 1000 % 60
            titleView?.text = "計時 : $minius : $seconds"
            if(minius<Constants.timeSettings)
                mHandler.postDelayed(this, 1000)
            else {
                showDialog("結果顯示","遊戲時間結束","重新開始",null,1)
                mHandler.removeCallbacks(this)
            }
        }
    };


}