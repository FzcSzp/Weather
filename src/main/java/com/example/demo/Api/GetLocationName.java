package com.example.demo.Api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public class GetLocationName {

    public static String name;

    public GetLocationName(String locationString) {

        getLocationName(locationString);

    }

    public static String getName(String locationString) {

        getLocationName(locationString);

        return name;

    }

    public static String getLocationName(String locationString) {

        StringBuilder response = null;

        try {

            String apiUrl = "https://geoapi.qweather.com/v2/city/lookup?location=" + locationString + "&key=xxxxxx";

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

                name = extractNameFromResponse(response.toString());

                System.out.println(name);

            } else {

                System.out.println("API 请求失败，响应码：" + responseCode);

            }

            connection.disconnect();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return name;
    }

    private static String extractNameFromResponse(String response) {

        int startIndex = response.indexOf("\"name\":") + 8;

        int endIndex = response.indexOf("\"", startIndex);

        return response.substring(startIndex, endIndex);

    }
}