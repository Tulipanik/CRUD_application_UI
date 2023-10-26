package UI.HttpRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class HttpRequest {

    private final String URLAddress = "http://localhost:8080/notes";
    HttpURLConnection connection = null;

    private String getRequest ( String endpoint) {
        try {
            URL url = new URL(URLAddress + endpoint);
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String postRequest (String endpoint, String json) {
        try {
            URL url = new URL(URLAddress + endpoint);
            System.out.println(URLAddress+endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.setRequestMethod("POST");

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = json.getBytes("UTF-8");
                os.write(input, 0, input.length);
            }

            System.out.println(json);

            int responseCode = connection.getResponseCode();
            System.out.println(responseCode);
            StringBuilder response = new StringBuilder();

            if (responseCode == 201) {
                System.out.println("nie");
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
            }
            return response.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getAllNotes () {
        return getRequest("");
    }

    public String getTitleNote (String id) {
        return getRequest(id);
    }

    public String add (String title, String content) throws MalformedURLException {
        HashMap <String, String> object = new HashMap<>();
        object.put("title", title);
        object.put("content", content);
        object.put("userId", "1");
        
        ObjectMapper writer = new ObjectMapper();
        try {
            String json = writer.writeValueAsString(object);
            return postRequest("", json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }



}
