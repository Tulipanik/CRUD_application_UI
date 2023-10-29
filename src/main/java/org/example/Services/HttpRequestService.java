package org.example.Services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestService implements HttpRequestServiceInterface {

    private String URLAddress;
    URL url = null;
    HttpURLConnection connection = null;

    public HttpRequestService(String URLAddress) {
        this.URLAddress = URLAddress;
    }

    public String getRequest (String endpoint) throws Exception {
        url = new URL(URLAddress + endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        StringBuilder response = new StringBuilder();

        if (responseCode == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
        }
        return response.toString();
    }

    public boolean postRequest (String endpoint, String json) throws Exception {
            url = new URL(URLAddress + endpoint);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.setRequestMethod("POST");

            OutputStream os = connection.getOutputStream();
            byte[] input = json.getBytes("UTF-8");
            os.write(input, 0, input.length);

            int responseCode = connection.getResponseCode();
            StringBuilder response = new StringBuilder();

            return responseCode == 201;
    }

    public boolean putRequest (String endpoint, String json) throws Exception{
        url = new URL(URLAddress + endpoint);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoInput(true);
        connection.setDoOutput(true);

        connection.setRequestMethod("PUT");

        OutputStream os = connection.getOutputStream();
        byte[] input = json.getBytes("UTF-8");
        os.write(input, 0, input.length);

        int responseCode = connection.getResponseCode();
        return responseCode == 201;
    }

    public boolean deleteRequest (String endpoint) throws Exception{
        url = new URL(URLAddress + endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");

        int responseCode = connection.getResponseCode();
        return responseCode == 204;
    }
}
