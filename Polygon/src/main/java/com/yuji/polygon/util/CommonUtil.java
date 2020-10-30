package com.yuji.polygon.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @className: CommonUtil
 * @description: 常用工具类
 * @author: yuji
 * @create: 2020-10-29 15:19:00
 */
public class CommonUtil {

    private final static String LETTER_26 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String randomUid(int len){
        String uid = "";
        if (len > 0){
            Random random = new Random();
            StringBuffer stringBuffer = new StringBuffer();
            for(int i = 0; i < len; i++){
                int number;
                if (i == 0 || i == 1){
                    number = random.nextInt(25);
                    stringBuffer.append(LETTER_26.charAt(number));
                }else{
                    number = random.nextInt(9);
                    stringBuffer.append(number);
                }

            }
            uid = stringBuffer.toString();
        }

        return uid;
    }

    public static Date getNowTime(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try{
            date = sdf.parse(sdf.format(date));
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
