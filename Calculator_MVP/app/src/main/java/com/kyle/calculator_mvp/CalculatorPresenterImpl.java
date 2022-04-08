package com.kyle.calculator_mvp;/*
 *@author Kyle Lin
 *@data 2022/4/7
 */

public class CalculatorPresenterImpl implements CalculatorPresenter , CalculatorListener{
    CalculatorView mainView;
    CalculatorModelImpl mainModel;

    public CalculatorPresenterImpl(CalculatorView mainView) {
        this.mainView = mainView;
        this.mainModel = new CalculatorModelImpl(this);
    }

    @Override
    public void initView() {
        mainView.setContentView();
    }

    @Override
    public void setClearAll() {
        mainModel.clearAllData();
    }

    @Override
    public void setDelete() {
        mainModel.deleteInputValue();
    }

    @Override
    public void setInput(String value) {
        mainModel.setInputValue(value);
    }

    @Override
    public void setAdd() {
        mainModel.calculate("+");
    }

    @Override
    public void setSub() {
        mainModel.calculate("-");
    }

    @Override
    public void setMult() {
        mainModel.calculate("*");
    }

    @Override
    public void setDiv() {
        mainModel.calculate("/");
    }

    @Override
    public void setEquals() {
        mainModel.equals();
    }

    @Override
    public void setSelectSign() {
        mainModel.selectSign();
    }

    @Override
    public void setPercentage() {
        mainModel.percentage();
    }

    @Override
    public void updateInputValue(Object value) {
        mainView.setText(value);
    }

    @Override
    public void updateResult(Object value) {
        mainView.setText(value);
    }
}
