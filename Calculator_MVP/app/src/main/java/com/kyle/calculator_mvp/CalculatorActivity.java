package com.kyle.calculator_mvp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalculatorActivity extends AppCompatActivity implements CalculatorView , View.OnClickListener {

    private CalculatorPresenterImpl mCalculatorPresenterImpl;
    private TextView txt_result;
    private Button btn_ac , btn_select_sign , btn_percentage,btn_del;
    private Button btn_0,btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9;
    private Button btn_dot,btn_equal,btn_plus,btn_minus,btn_mult,btn_div;
    private String inputValue = null;
    private double result = 0;
    private String sign = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCalculatorPresenterImpl = new CalculatorPresenterImpl(this);
        mCalculatorPresenterImpl.initView();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_calculator_mainview);
        findView();
        setListener();
    }

    @Override
    public void setText(Object c) {
        txt_result.setText(""+c);
    }

    private void findView(){
        txt_result = findViewById(R.id.textresult);

        btn_ac = findViewById(R.id.bt_clear);
        btn_select_sign = findViewById(R.id.bt_select_sign);
        btn_percentage = findViewById(R.id.bt_percentage);
        btn_del = findViewById(R.id.bt_delete);

        btn_0=findViewById(R.id.bt_num0);
        btn_1=findViewById(R.id.bt_num1);
        btn_2=findViewById(R.id.bt_num2);
        btn_3=findViewById(R.id.bt_num3);
        btn_4=findViewById(R.id.bt_num4);
        btn_5=findViewById(R.id.bt_num5);
        btn_6=findViewById(R.id.bt_num6);
        btn_7=findViewById(R.id.bt_num7);
        btn_8=findViewById(R.id.bt_num8);
        btn_9=findViewById(R.id.bt_num9);

        btn_dot=findViewById(R.id.bt_dot);
        btn_equal=findViewById(R.id.bt_equal);

        btn_plus=findViewById(R.id.bt_plus);
        btn_minus=findViewById(R.id.bt_minus);
        btn_mult=findViewById(R.id.bt_mult);
        btn_div=findViewById(R.id.bt_div);
    }

    private void setListener(){
        btn_ac.setOnClickListener(this);
        btn_select_sign.setOnClickListener(this);
        btn_percentage.setOnClickListener(this);
        btn_del.setOnClickListener(this);

        btn_0.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);

        btn_dot.setOnClickListener(this);
        btn_equal.setOnClickListener(this);

        btn_plus.setOnClickListener(this);
        btn_minus.setOnClickListener(this);
        btn_mult.setOnClickListener(this);
        btn_div.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v==btn_ac){
            mCalculatorPresenterImpl.setClearAll();
        }

        if(v==btn_select_sign){
            mCalculatorPresenterImpl.setSelectSign();
        }

        if(v==btn_percentage){
            mCalculatorPresenterImpl.setPercentage();
        }

        if(v==btn_del){
            mCalculatorPresenterImpl.setDelete();
        }

        if(v==btn_0){
            mCalculatorPresenterImpl.setInput("0");
        }

        if(v==btn_1){
            mCalculatorPresenterImpl.setInput("1");
        }

        if(v==btn_2){
            mCalculatorPresenterImpl.setInput("2");
        }

        if(v==btn_3){
            mCalculatorPresenterImpl.setInput("3");
        }

        if(v==btn_4){
            mCalculatorPresenterImpl.setInput("4");
        }

        if(v==btn_5){
            mCalculatorPresenterImpl.setInput("5");
        }

        if(v==btn_6){
            mCalculatorPresenterImpl.setInput("6");
        }

        if(v==btn_7){
            mCalculatorPresenterImpl.setInput("7");
        }

        if(v==btn_8){
            mCalculatorPresenterImpl.setInput("8");
        }

        if(v==btn_9){
            mCalculatorPresenterImpl.setInput("9");
        }

        if(v==btn_dot){
            mCalculatorPresenterImpl.setInput(".");
        }

        if(v==btn_equal){
            mCalculatorPresenterImpl.setEquals();
        }

        if(v==btn_plus){
            mCalculatorPresenterImpl.setAdd();
        }

        if(v==btn_minus){
            mCalculatorPresenterImpl.setSub();
        }

        if(v==btn_mult){
            mCalculatorPresenterImpl.setMult();
        }

        if(v==btn_div){
            mCalculatorPresenterImpl.setDiv();
        }
    }
}