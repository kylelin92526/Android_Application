package com.kyle.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Calculator extends AppCompatActivity implements View.OnClickListener {
    private static String TAG = "kyle";
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
        setContentView(R.layout.activity_calculator_mainview);

        findView();
        setListener();
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

    private void equalsFuntion(){
        if(sign==null){
            return;
        }

        if(sign.equals("+")){
            if(inputValue!=null)
                result=result+Double.parseDouble(inputValue);
        }
        if(sign.equals("-")){
            if(inputValue!=null)
                result=result-Double.parseDouble(inputValue);
        }
        if(sign.equals("*")){
            if(inputValue!=null)
                result=result*Double.parseDouble(inputValue);
        }
        if(sign.equals("/")){
            if(inputValue!=null)
                result=result/Double.parseDouble(inputValue);
        }
        if(sign!=null) {
            txt_result.setText("" + result);
            sign = null;
        }
        inputValue = null;

    }


    @Override
    public void onClick(View v) {
        if(v==btn_ac){
            result=0;
            inputValue=null;
            sign=null;
            txt_result.setText("0");
        }

        if(v==btn_select_sign){
            if(inputValue!=null) {
                if(Double.parseDouble(inputValue)>0) {
                    inputValue = "-" + inputValue;
                    txt_result.setText(inputValue);
                }else{
                    String input[]=inputValue.split("-");
                    inputValue=null;
                    for(String input1:input){
                        if(!input1.trim().equals("") && input1.trim()!=null){
                            inputValue=input1;
                            Log.d(TAG,input1);
                        }

                    }
                    txt_result.setText(inputValue);
                }
            }else{
                if(inputValue==null && result!=0){
                    result=-result;
                    txt_result.setText(""+result);
                }
            }
        }

        if(v==btn_percentage){
            if(result == 0 && inputValue != null && !inputValue.equals(""))
                result = Double.parseDouble(inputValue);
            sign = null;
            inputValue = null;
            result = result/100;
            txt_result.setText(""+result);
        }

        if(v==btn_del){
            if(inputValue!=null){
                inputValue=null;
            }
            txt_result.setText(""+result);
        }

        if(v==btn_0){
            if(inputValue==null){
                inputValue="0";
            }else{
                inputValue=inputValue+"0";
            }
            txt_result.setText(inputValue);
        }

        if(v==btn_1){
            if(inputValue==null){
                inputValue="1";
            }else{
                inputValue=inputValue+"1";
            }
            txt_result.setText(inputValue);
        }

        if(v==btn_2){
            if(inputValue==null){
                inputValue="2";
            }else{
                inputValue=inputValue+"2";
            }
            txt_result.setText(inputValue);
        }

        if(v==btn_3){
            if(inputValue==null){
                inputValue="3";
            }else{
                inputValue=inputValue+"3";
            }
            txt_result.setText(inputValue);
        }

        if(v==btn_4){
            if(inputValue==null){
                inputValue="4";
            }else{
                inputValue=inputValue+"4";
            }
            txt_result.setText(inputValue);
        }

        if(v==btn_5){
            if(inputValue==null){
                inputValue="5";
            }else{
                inputValue=inputValue+"5";
            }
            txt_result.setText(inputValue);
        }

        if(v==btn_6){
            if(inputValue==null){
                inputValue="6";
            }else{
                inputValue=inputValue+"6";
            }
            txt_result.setText(inputValue);
        }

        if(v==btn_7){
            if(inputValue==null){
                inputValue="7";
            }else{
                inputValue=inputValue+"7";
            }
            txt_result.setText(inputValue);
        }

        if(v==btn_8){
            if(inputValue==null){
                inputValue="8";
            }else{
                inputValue=inputValue+"8";
            }
            txt_result.setText(inputValue);
        }

        if(v==btn_9){
            if(inputValue==null){
                inputValue="9";
            }else{
                inputValue=inputValue+"9";
            }
            txt_result.setText(inputValue);
        }

        if(v==btn_dot){
            if(inputValue==null){
                inputValue=".";
            }else{
                inputValue=inputValue+".";
            }
            txt_result.setText(inputValue);
        }

        if(v==btn_equal){
            equalsFuntion();
        }

        if(v==btn_plus){
            equalsFuntion();
           sign="+";
           if(inputValue!=null) {
               if(result==0){
                   result = Double.parseDouble(inputValue);
               }else{
                   result = result + Double.parseDouble(inputValue);
               }
               inputValue = null;
           }
           txt_result.setText(""+result);
        }

        if(v==btn_minus){
            equalsFuntion();
            sign="-";
            if(inputValue!=null) {
                if(result==0){
                    result = Double.parseDouble(inputValue);
                }else{
                    result = result - Double.parseDouble(inputValue);
                }
                inputValue = null;
            }
            txt_result.setText(""+result);
        }

        if(v==btn_mult){
            equalsFuntion();
            sign="*";
            if(inputValue!=null) {
                if(result==0){
                    result = Double.parseDouble(inputValue);
                }else{
                    result = result * Double.parseDouble(inputValue);
                }
                 inputValue = null;
            }
            txt_result.setText(""+result);
        }

        if(v==btn_div){
            equalsFuntion();
            sign="/";
            if(inputValue!=null) {
                if(result==0){
                    result = Double.parseDouble(inputValue);
                }else{
                    result = result / Double.parseDouble(inputValue);
                }
            }
            txt_result.setText(""+result);
        }

    }
}