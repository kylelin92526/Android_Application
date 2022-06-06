package com.kyle.demouibykotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(){
    lateinit var arraylist:Array<String>
    var testList:MutableList<String> = mutableListOf()
    lateinit var listview: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*
           List view 的 作法
           我用一個 array 讀取 res 中的 array.xml 裡的 test_list
           用集合的方式 mutableListOf() 去新增所需要的項目
           帶入 ArrayAdapter
         */
        listview= findViewById(R.id.listview)
        arraylist = resources.getStringArray(R.array.test_list)
        for(str in arraylist){
            //if(!str.equals("Linearlayout")){
                testList.add(str)
            //}
        }
        var adapter= ArrayAdapter(this,android.R.layout.simple_list_item_1,testList)

        listview.adapter = adapter //listview.setAdapter(adapter)

        listview.onItemClickListener = itemClickListener //listview.setOnItemClickListener(itemClickListener)


       /* listview.setOnItemClickListener { parent, view, position, id  ->
           var str_position = parent.getItemAtPosition(position) as String
           when(str_position){
               testList[0] -> Log.d("kyle0",str_position)
               testList[1] -> Log.d("kyle1",str_position)
               testList[2] -> Log.d("kyle2",str_position)
               testList[3] -> Log.d("kyle3",str_position)
               testList[4] -> Log.d("kyle4",str_position)
           }

        }*/
    }
    var itemClickListener:AdapterView.OnItemClickListener = object:AdapterView.OnItemClickListener{
        override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            var str_position = p0?.getItemAtPosition(p2) as String
            when(str_position) {
                testList[0] -> { //Linearlayout
                    Log.d("kyle0", str_position)
                    gotoActivity (LinearLayoutActivity::class.java)
                }
                testList[1] -> {//RelativeLayout
                    Log.d("kyle1", str_position)
                    gotoActivity(RelativeLayoutMainActivity::class.java)
                }
                testList[2] -> {//TableLayout
                    Log.d("kyle2", str_position)
                    gotoActivity(TableLayoutActivity::class.java)
                }
                testList[3] -> {//AbsoluteLayout
                    Log.d("kyle3", str_position)
                    gotoActivity(AbsoluteLayoutActivity::class.java)
                }
                testList[4] -> {//FrameLayout
                    Log.d("kyle4", str_position)
                    gotoActivity(FrameLayoutActivity::class.java)
                }

                testList[5] -> {//ConstraintLayout
                    Log.d("kyle5", str_position)
                    gotoActivity(ConstraintLayoutActivity::class.java)
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        Log.d("kyle","onResume")
    }

    fun gotoActivity(activity: Class<*>?) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }


}