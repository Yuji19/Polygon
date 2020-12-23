package com.yuji.polygon.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @className: DateConverter
 * @description: 字符串转日期
 * @author: yuji
 * @create: 2020-10-15 20:59
 **/


@Component
public class StringToDateConverter implements Converter<String, Date> {

    SimpleDateFormat sdf;

    @Override
    public Date convert(String source) {
        if (source != null && !"".equals(source)){
            try {
                if (source.contains(":")){
                    sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                }else {
                    sdf = new SimpleDateFormat("yyyy-MM-dd");
                }
                return sdf.parse(source);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
