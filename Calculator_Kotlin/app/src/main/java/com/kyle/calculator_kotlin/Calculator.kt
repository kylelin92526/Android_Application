package com.kyle.calculator_kotlin

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class Calculator : AppCompatActivity() ,View.OnClickListener{

    private lateinit var txt_result: TextView
    private lateinit var btn_ac : Button
    private lateinit var btn_select_sign : Button
    private lateinit var btn_percentage : Button
    private lateinit var btn_del : Button
    private lateinit var btn_0 : Button
    private lateinit var btn_1 : Button
    private lateinit var btn_2 : Button
    private lateinit var btn_3: Button
    private lateinit var btn_4 : Button
    private lateinit var btn_5 : Button
    private lateinit var btn_6 : Button
    private lateinit var btn_7 : Button
    private lateinit var btn_8 : Button
    private lateinit var btn_9 : Button
    private lateinit var btn_dot : Button
    private lateinit var btn_equal : Button
    private lateinit var btn_plus : Button
    private lateinit var btn_minus : Button
    private lateinit var btn_mult : Button
    private lateinit var btn_div : Button
    private var inputValue: String? = null
    private var result = 0.0
    private var sign: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator_mainview)
        findView();
        setListener();
    }

    private fun findView() {
        txt_result = findViewById(R.id.textresult)
        btn_ac = findViewById(R.id.bt_clear)
        btn_select_sign = findViewById(R.id.bt_select_sign)
        btn_percentage = findViewById(R.id.bt_percentage)
        btn_del = findViewById(R.id.bt_delete)
        btn_0 = findViewById(R.id.bt_num0)
        btn_1 = findViewById(R.id.bt_num1)
        btn_2 = findViewById(R.id.bt_num2)
        btn_3 = findViewById(R.id.bt_num3)
        btn_4 = findViewById(R.id.bt_num4)
        btn_5 = findViewById(R.id.bt_num5)
        btn_6 = findViewById(R.id.bt_num6)
        btn_7 = findViewById(R.id.bt_num7)
        btn_8 = findViewById(R.id.bt_num8)
        btn_9 = findViewById(R.id.bt_num9)
        btn_dot = findViewById(R.id.bt_dot)
        btn_equal = findViewById(R.id.bt_equal)
        btn_plus = findViewById(R.id.bt_plus)
        btn_minus = findViewById(R.id.bt_minus)
        btn_mult = findViewById(R.id.bt_mult)
        btn_div = findViewById(R.id.bt_div)
    }

    private fun setListener() {
        btn_ac.setOnClickListener(this)
        btn_select_sign.setOnClickListener(this)
        btn_percentage.setOnClickListener(this)
        btn_del.setOnClickListener(this)
        btn_0.setOnClickListener(this)
        btn_1.setOnClickListener(this)
        btn_2.setOnClickListener(this)
        btn_3.setOnClickListener(this)
        btn_4.setOnClickListener(this)
        btn_5.setOnClickListener(this)
        btn_6.setOnClickListener(this)
        btn_7.setOnClickListener(this)
        btn_8.setOnClickListener(this)
        btn_9.setOnClickListener(this)
        btn_dot.setOnClickListener(this)
        btn_equal.setOnClickListener(this)
        btn_plus.setOnClickListener(this)
        btn_minus.setOnClickListener(this)
        btn_mult.setOnClickListener(this)
        btn_div.setOnClickListener(this)
    }

    private fun equalsFuntion() {
        if (sign == null) {
            return
        }
        if (sign == "+") {
            if (inputValue != null) result = result + inputValue!!.toDouble()
        }
        if (sign == "-") {
            if (inputValue != null) result = result - inputValue!!.toDouble()
        }
        if (sign == "*") {
            if (inputValue != null) result = result * inputValue!!.toDouble()
        }
        if (sign == "/") {
            if (inputValue != null) result = result / inputValue!!.toDouble()
        }
        if (sign != null) {
            txt_result.text = "" + result
            sign = null
        }
        inputValue = null
    }

    override fun onClick(v: View?) {
        if (v === btn_ac) {
            result = 0.0
            inputValue = null
            sign = null
            txt_result.text = "0"
        }

        if (v === btn_select_sign) {
            if (inputValue != null) {
                if (inputValue!!.toDouble() > 0) {
                    inputValue = "-$inputValue"
                    txt_result.text = inputValue
                } else {
                    val input = inputValue!!.split("-").toTypedArray()
                    inputValue = null
                    for (input1 in input) {
                        if (input1.trim { it <= ' ' } != "" && input1.trim { it <= ' ' } != null) {
                            inputValue = input1
                        }
                    }

                    txt_result.text = inputValue
                }
            } else {
                if (inputValue == null && result != 0.0) {
                    result = -result
                    txt_result.text = "" + result
                }
            }
        }

        if (v === btn_percentage) {
            if (result == 0.0 && inputValue != null && inputValue != "")
                result = inputValue!!.toDouble()
            sign = null
            inputValue = null
            result = result / 100
            txt_result.text = "" + result
        }

        if (v === btn_del) {
            if (inputValue != null) {
                inputValue = null
            }
            txt_result.text = "" + result
        }

        if (v === btn_0) {
            inputValue = if (inputValue == null) {
                "0"
            } else {
                inputValue + "0"
            }
            txt_result.text = inputValue
        }

        if (v === btn_1) {
            inputValue = if (inputValue == null) {
                "1"
            } else {
                inputValue + "1"
            }
            txt_result.text = inputValue
        }

        if (v === btn_2) {
            inputValue = if (inputValue == null) {
                "2"
            } else {
                inputValue + "2"
            }
            txt_result.text = inputValue
        }

        if (v === btn_3) {
            inputValue = if (inputValue == null) {
                "3"
            } else {
                inputValue + "3"
            }
            txt_result.text = inputValue
        }

        if (v === btn_4) {
            inputValue = if (inputValue == null) {
                "4"
            } else {
                inputValue + "4"
            }
            txt_result.text = inputValue
        }

        if (v === btn_5) {
            inputValue = if (inputValue == null) {
                "5"
            } else {
                inputValue + "5"
            }
            txt_result.text = inputValue
        }

        if (v === btn_6) {
            inputValue = if (inputValue == null) {
                "6"
            } else {
                inputValue + "6"
            }
            txt_result.text = inputValue
        }

        if (v === btn_7) {
            inputValue = if (inputValue == null) {
                "7"
            } else {
                inputValue + "7"
            }
            txt_result.text = inputValue
        }

        if (v === btn_8) {
            inputValue = if (inputValue == null) {
                "8"
            } else {
                inputValue + "8"
            }
            txt_result.text = inputValue
        }

        if (v === btn_9) {
            inputValue = if (inputValue == null) {
                "9"
            } else {
                inputValue + "9"
            }
            txt_result.text = inputValue
        }

        if (v === btn_dot) {
            inputValue = if (inputValue == null) {
                "."
            } else {
                "$inputValue."
            }
            txt_result.text = inputValue
        }

        if (v === btn_equal) {
            equalsFuntion()
        }

        if (v === btn_plus) {
            equalsFuntion()
            sign = "+"
            if (inputValue != null) {
                result = if (result == 0.0) {
                    inputValue!!.toDouble()
                } else {
                    result + inputValue!!.toDouble()
                }
                inputValue = null
            }
            txt_result.text = "" + result
        }

        if (v === btn_minus) {
            equalsFuntion()
            sign = "-"
            if (inputValue != null) {
                result = if (result == 0.0) {
                    inputValue!!.toDouble()
                } else {
                    result - inputValue!!.toDouble()
                }
                inputValue = null
            }
            txt_result.text = "" + result
        }

        if (v === btn_mult) {
            equalsFuntion()
            sign = "*"
            if (inputValue != null) {
                result = if (result == 0.0) {
                    inputValue!!.toDouble()
                } else {
                    result * inputValue!!.toDouble()
                }
                inputValue = null
            }
            txt_result.text = "" + result
        }

        if (v === btn_div) {
            equalsFuntion()
            sign = "/"
            if (inputValue != null) {
                result = if (result == 0.0) {
                    inputValue!!.toDouble()
                } else {
                    result / inputValue!!.toDouble()
                }
            }
            txt_result.text = "" + result
        }
    }
}