package com.byd.emg.resultData;

//大罐数、中罐数、小罐数的包装类
public class TankNumberData {

    private String bigPotNumbers;         //大罐数

    private String middlePotNumbers;       //中灌数

    private String smallPotNumbers;        //小罐数

    public TankNumberData() {}

    public String getBigPotNumbers() {
        return bigPotNumbers;
    }

    public void setBigPotNumbers(String bigPotNumbers) {
        this.bigPotNumbers = bigPotNumbers;
    }

    public String getMiddlePotNumbers() {
        return middlePotNumbers;
    }

    public void setMiddlePotNumbers(String middlePotNumbers) {
        this.middlePotNumbers = middlePotNumbers;
    }

    public String getSmallPotNumbers() {
        return smallPotNumbers;
    }

    public void setSmallPotNumbers(String smallPotNumbers) {
        this.smallPotNumbers = smallPotNumbers;
    }
}
