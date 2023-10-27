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

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.setRequestMethod("POST");

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = json.getBytes("UTF-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            StringBuilder response = new StringBuilder();

            if (responseCode == 201) {
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

    private boolean putRequest (String endpoint, String json) {
        try {
            URL url = new URL(URLAddress + endpoint);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.setRequestMethod("PUT");

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = json.getBytes("UTF-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();

            return responseCode == 201;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean deleteRequest (String endpoint) {
        try {
            URL url = new URL(URLAddress + endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            int responseCode = connection.getResponseCode();

            return responseCode == 204;
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

    public String getGroupNotes (String userId) {
        return  getRequest("/user/" + userId);
    }

    public String add (String title, String content, String groupId) throws MalformedURLException {
        HashMap <String, String> object = new HashMap<>();
        object.put("title", title);
        object.put("content", content);
        object.put("userId", groupId);
        
        ObjectMapper writer = new ObjectMapper();
        try {
            String json = writer.writeValueAsString(object);
            return postRequest("", json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateNote (String id, String title, String content, String groupId) {
        HashMap <String, String> object = new HashMap<>();
        object.put("id", id);
        object.put("title", title);
        object.put("content", content);
        object.put("userId", groupId);

        ObjectMapper writer = new ObjectMapper();
        try {
            String json = writer.writeValueAsString(object);
            return putRequest("/" + id, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteChosenNote (String id) {
        return deleteRequest("/" + id);
    }

    public boolean deleteAllNotes () {
        return deleteRequest("");
    }

    public boolean deleteAllGroupNotes (String groupId) {
        return deleteRequest("/user/" + groupId);
    }

}
