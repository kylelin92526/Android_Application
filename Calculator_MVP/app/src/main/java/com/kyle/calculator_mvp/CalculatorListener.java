package com.kyle.calculator_mvp;/*
 *@author Kyle Lin
 *@data 2022/4/7
 */

public interface CalculatorListener {
    void updateInputValue(Object value);
    void updateResult(Object value);
}
