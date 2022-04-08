package com.kyle.calculator_mvp;
/*
 *@author Kyle Lin
 *@data 2022/4/7
 */

public class CalculatorModelImpl implements CalculatorModel{
    private String inputValue = null;
    private double result = 0;
    private String sign = null;
    private CalculatorListener mListener;

    public CalculatorModelImpl(CalculatorListener Listener) {
        mListener=Listener;
    }

    @Override
    public void clearAllData() {
        result=0;
        inputValue=null;
        sign=null;
        mListener.updateResult("0");
    }

    @Override
    public void deleteInputValue() {
        if(inputValue!=null){
            inputValue=null;
        }
        mListener.updateInputValue("0");
    }

    @Override
    public void setInputValue(String value) {
        if(inputValue==null){
            inputValue=value;
        }else{
            inputValue=inputValue+value;
        }
        mListener.updateInputValue(inputValue);
    }

    @Override
    public void calculate(String str) {
        equals();
        sign = str;
        if(inputValue!=null) {
            if(result==0){
                result = Double.parseDouble(inputValue);
            }else{
                switch(str){
                    case "+":
                        result = result + Double.parseDouble(inputValue);
                        break;
                    case "-":
                        result = result - Double.parseDouble(inputValue);
                        break;
                    case "*":
                        result = result * Double.parseDouble(inputValue);
                        break;
                    case "/":
                        result = result / Double.parseDouble(inputValue);
                        break;
                }
            }
            inputValue = null;
        }
        mListener.updateResult(result);
    }


    @Override
    public void equals() {
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
            mListener.updateResult(result);
            sign = null;
        }
        inputValue = null;

    }

    @Override
    public void selectSign() {
        if(inputValue!=null) {
            if(Double.parseDouble(inputValue)>0) {
                inputValue = "-" + inputValue;
                mListener.updateInputValue(inputValue);
            }else{
                String input[]=inputValue.split("-");
                inputValue=null;
                for(String input1:input){
                    if(!input1.trim().equals("") && input1.trim()!=null){
                        inputValue=input1;
                    }
                }
                mListener.updateInputValue(inputValue);
            }
        }else{
            if(inputValue==null && result!=0){
                result=-result;
                mListener.updateResult(result);
            }
        }
    }

    @Override
    public void percentage() {
        if(result == 0 && inputValue != null && !inputValue.equals(""))
            result = Double.parseDouble(inputValue);
        sign = null;
        inputValue = null;
        result = result/100;
        mListener.updateResult(result);
    }
}
