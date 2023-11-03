package org.example.Services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import static java.net.HttpURLConnection.*;
import java.net.URL;

public class HttpRequestService implements HttpRequestServiceInterface {
    public static final String GET_Method = "GET";
    public static final String POST_Method = "POST";
    public static final String PUT_Method = "PUT";
    public static final String DELETE_Method = "DELETE";

    private String defaultCharset = "UTF-8";

    public HttpRequestService() {}
    public HttpRequestService(String defaultCharset) {
        this.defaultCharset = defaultCharset;
    }

    public String getRequest (URL url) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(GET_Method);

        int responseCode = connection.getResponseCode();

        StringBuilder response = new StringBuilder();

        if (responseCode == HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
        }
        return response.toString();
    }

    public boolean postRequest (URL url, String json) throws Exception {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.setRequestMethod(POST_Method);

            OutputStream os = connection.getOutputStream();
            byte[] input = json.getBytes(defaultCharset);
            os.write(input, 0, input.length);

            int responseCode = connection.getResponseCode();
            StringBuilder response = new StringBuilder();

            return responseCode == HTTP_CREATED;
    }

    public boolean putRequest (URL url, String json) throws Exception{
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoInput(true);
        connection.setDoOutput(true);

        connection.setRequestMethod(PUT_Method);

        OutputStream os = connection.getOutputStream();
        byte[] input = json.getBytes(defaultCharset);
        os.write(input, 0, input.length);

        int responseCode = connection.getResponseCode();
        return responseCode == HTTP_CREATED;
    }

    public boolean deleteRequest (URL url) throws Exception{
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(DELETE_Method);

        int responseCode = connection.getResponseCode();
        return responseCode == 204;
    }

    public String getDefaultCharset()
    {
        return defaultCharset;
    }

    public void setDefaultCharset(String defaultCharset)
    {
        this.defaultCharset = defaultCharset;
    }
}
