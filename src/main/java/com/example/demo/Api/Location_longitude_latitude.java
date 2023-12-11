package com.example.demo.Api;

import java.text.DecimalFormat;

public class Location_longitude_latitude {

    public String getLocation(String latitude, String longitude) {

        // 创建DecimalFormat对象，用于格式化经纬度值，保留两位小数
        DecimalFormat df = new DecimalFormat("#.##");

        // 将输入的纬度值转换为双精度浮点数，并格式化
        String formattedLatitude = df.format(Double.parseDouble(latitude));

        // 将输入的经度值转换为双精度浮点数，并格式化
        String formattedLongitude = df.format(Double.parseDouble(longitude));

        // 拼接格式化后的经度和纬度，用逗号分隔
        String Location = formattedLongitude + "," + formattedLatitude;


        System.out.println(Location);

        // 返回格式化后的经纬度字符串
        return Location;
    }
}