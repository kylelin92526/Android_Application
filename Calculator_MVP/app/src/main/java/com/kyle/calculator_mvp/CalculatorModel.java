package com.kyle.calculator_mvp;/*
 *@author Kyle Lin
 *@data 2022/4/7
 */

public interface CalculatorModel {
    void clearAllData();
    void deleteInputValue();
    void setInputValue(String value);
    void calculate(String str);
    void equals();
    void selectSign();
    void percentage();
}
