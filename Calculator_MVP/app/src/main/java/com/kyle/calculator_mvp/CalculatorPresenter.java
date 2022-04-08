package com.kyle.calculator_mvp;
/*
 *@author Kyle Lin
 *@data 2022/4/7
 */

public interface CalculatorPresenter {
    void initView();
    void setClearAll();
    void setDelete();
    void setInput(String value);
    void setAdd();
    void setSub();
    void setMult();
    void setDiv();
    void setEquals();
    void setSelectSign();
    void setPercentage();
}
