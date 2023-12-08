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

    @GetMapping("/getCityList")
    @ResponseBody
    public List<Map<String, String>> getCityList(@RequestParam("city") String city) {

        List<Map<String, String>> cityList = queryAndGetData(city);

        return cityList;

    }

    private List<Map<String, String>> queryAndGetData(String city) {

        String url = "jdbc:sqlite:LocationID.db";

        List<Map<String, String>> cityList = new ArrayList<>();

        try {

            Connection connection = DriverManager.getConnection(url);

            Statement statement = connection.createStatement();

            String selectSQL = "SELECT * FROM CityList WHERE Location_Name_ZH LIKE '%" + city + "%'";

            ResultSet resultSet = statement.executeQuery(selectSQL);

            while (resultSet.next()) {

                String locationID = resultSet.getString("Location_ID");

                String locationNameZH = resultSet.getString("Location_Name_ZH");

                Map<String, String> cityData = new HashMap<>();

                cityData.put("Location_ID", locationID);

                cityData.put("Location_Name_ZH", locationNameZH);

                cityList.add(cityData);
            }

            resultSet.close();

            statement.close();

            connection.close();

        } catch (SQLException e) {

            System.err.println("没有此城市：" + e.getMessage());

        }

        return cityList;
    }
}