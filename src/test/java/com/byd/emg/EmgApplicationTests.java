package com.byd.emg;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmgApplicationTests {

    @Test
    public void contextLoads() {
        /*String date="2018-09-29 01:00:00";
        String d=date.substring(11);
        System.out.println(d);
        String c="01:00:00";*/
        /*DecimalFormat df = new DecimalFormat("0");
        System.out.println(df.format(0.000));*/
        /*DecimalFormat df=(DecimalFormat) NumberFormat.getInstance();
        df.setMaximumFractionDigits(2);*/
        //System.out.println("=====>>>"+(234242.564/100));
        /*String str = "[\\u4e00-\\u9fa5]*";
        Pattern pattern = Pattern.compile(str);
        String mStr = "FIT-092101-01-TE";
        Matcher m = pattern.matcher(mStr);
        if(m.matches()){
            System.out.println("匹配内容："+m.group());
        }*/
        String s="12";
        String regex="^[+-]?\\d+(\\.\\d+)?$";
        if(s.matches(regex)==true){
            System.out.println("匹配");
        }else{
            System.out.println("不匹配");
        }


    }

    private  String parseFormate(double data){
        int val = (int)Math.abs(data);
        String form;
        if (val/100 <=0 && val >0){// 1-100之间
            form = "0.0000";
        }else{
            // 0.1-0.01之间
            int val2 = (int)(Math.abs(data)*10);
            if (val2 > 0 && Math.abs(data) <1){
                form = "0.0000";
            }else{
                form = "0.0000e0";
            }
            if (data == 0){
                form = "0.0000";
            }
        }
        BigDecimal bigDecimal = new BigDecimal(data,new MathContext(2));
        NumberFormat formatter = new DecimalFormat(form);
        return formatter.format(Double.valueOf(bigDecimal.toPlainString()));
    }

}
