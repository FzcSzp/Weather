package com.example.demo.Api;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:63342")

public class GetWeather {
    public String getNowWeather(String locationString) {


        StringBuilder response = null;
        try {

            String apiUrl = "https://devapi.qweather.com/v7/weather/now?location=" + locationString + "&key=16586a8b48d949dfa51376a1ce73b232";

            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            connection.setRequestProperty("Accept-Encoding", "gzip");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {

                String contentEncoding = connection.getHeaderField("Content-Encoding");

                BufferedReader reader;

                if ("gzip".equals(contentEncoding)) {

                    reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getInputStream())));
                } else {

                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                }

                response = new StringBuilder();

                String line;

                while ((line = reader.readLine()) != null) {

                    response.append(line);
                }

                reader.close();

                // 输出获取的数据
                System.out.println("API 响应数据：" + response);
            } else {
                System.out.println("API 请求失败，响应码：" + responseCode);
            }

            connection.disconnect();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return response.toString();

    }

    public String getDailyWeather(String locationString) {

        StringBuilder response = null;
        try {

            String apiUrl = "https://devapi.qweather.com/v7/weather/7d?location=" + locationString + "&key=16586a8b48d949dfa51376a1ce73b232";

            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            connection.setRequestProperty("Accept-Encoding", "gzip");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {

                String contentEncoding = connection.getHeaderField("Content-Encoding");

                BufferedReader reader;
                if ("gzip".equals(contentEncoding)) {

                    reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getInputStream())));

                } else {

                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                }

                response = new StringBuilder();

                String line;

                while ((line = reader.readLine()) != null) {

                    response.append(line);

                }

                reader.close();

                System.out.println("API 响应数据：" + response);

            } else {

                System.out.println("API 请求失败，响应码：" + responseCode);

            }

            connection.disconnect();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return response.toString();

    }

    @GetMapping("/locality")
    @ResponseBody
    public String getLocalityWeather(@RequestHeader("latitude") float latitude,
                                    @RequestHeader("longitude") float longitude) {

        String latitudeString = Float.toString(latitude);

        String longitudeString = Float.toString(longitude);

        String locationString = new Location_longitude_latitude().getLocation(latitudeString, longitudeString).toString();

        String  Now = getNowWeather(locationString);

        String  Daily = getDailyWeather(locationString);

        GetLocationName Name=new GetLocationName(locationString);

        String cityName = GetLocationName.getName(locationString);

        System.out.println("城市:" + Name);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode nowNode = objectMapper.readTree(Now);
            JsonNode dailyNode = objectMapper.readTree(Daily);
            ObjectNode combinedNode = objectMapper.createObjectNode();
            combinedNode.setAll((ObjectNode) nowNode);
            combinedNode.set("daily", dailyNode.get("daily"));
            combinedNode.put("cityName", String.valueOf(cityName));
            System.out.println(objectMapper.writeValueAsString(combinedNode));
            return objectMapper.writeValueAsString(combinedNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{\"error\":\"处理 JSON 数据时发生错误\"}";
        }

    }
    @GetMapping("/othen")
    @ResponseBody
    public String getOthenWeather(@RequestHeader("Location_ID") String Location_ID) {

        String  Now = getNowWeather(Location_ID);
        String  Daily = getDailyWeather(Location_ID);
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode nowNode = objectMapper.readTree(Now);
            JsonNode dailyNode = objectMapper.readTree(Daily);


            ObjectNode combinedNode = objectMapper.createObjectNode();
            combinedNode.setAll((ObjectNode) nowNode);
            combinedNode.set("daily", dailyNode.get("daily"));

            System.out.println(objectMapper.writeValueAsString(combinedNode));
            return objectMapper.writeValueAsString(combinedNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{\"error\":\"处理 JSON 数据时发生错误\"}";
        }

    }
    }



