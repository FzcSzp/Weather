package com.example.demo.Api;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:63342")
public class GetCityList {

    // 处理GET请求，映射到"/api/getCityList"路径
    @GetMapping("/getCityList")
    @ResponseBody
    public List<Map<String, String>> getCityList(@RequestParam("city") String city) {

        // 调用方法查询并获取城市列表数据
        List<Map<String, String>> cityList = queryAndGetData(city);

        return cityList;
    }

    // 查询并获取城市列表数据的私有方法
    private List<Map<String, String>> queryAndGetData(String city) {

        // SQLite数据库连接URL
        String url = "jdbc:sqlite:LocationID.db";

        // 存储城市数据的List
        List<Map<String, String>> cityList = new ArrayList<>();

        try {
            // 建立数据库连接
            Connection connection = DriverManager.getConnection(url);

            // 创建数据库查询语句
            Statement statement = connection.createStatement();

            // 构建SQL查询语句，根据输入的城市名称模糊查询
            String selectSQL = "SELECT * FROM CityList WHERE Location_Name_ZH LIKE '%" + city + "%'";

            // 执行查询
            ResultSet resultSet = statement.executeQuery(selectSQL);

            // 处理查询结果集
            while (resultSet.next()) {
                // 从结果集中获取城市数据
                String locationID = resultSet.getString("Location_ID");
                String locationNameZH = resultSet.getString("Location_Name_ZH");

                // 创建存储城市数据的Map
                Map<String, String> cityData = new HashMap<>();

                // 将城市数据放入Map
                cityData.put("Location_ID", locationID);
                cityData.put("Location_Name_ZH", locationNameZH);

                // 将Map添加到城市数据列表中
                cityList.add(cityData);
            }

            // 关闭结果集、查询语句和数据库连接
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            // 捕获SQL异常，输出错误信息
            System.err.println("没有此城市：" + e.getMessage());
        }

        // 返回城市数据列表
        return cityList;
    }
}