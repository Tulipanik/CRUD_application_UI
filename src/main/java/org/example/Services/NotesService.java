package org.example.Services;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class NotesService implements NotesServiceInterface {
    private static final String baseUrl = "http://localhost:8080/notes";
    private final HttpRequestServiceInterface requests = new HttpRequestService();

    public String getAllNotes() {
        try {
            URL url = new URL(baseUrl);
            return requests.getRequest(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getIdNote(String id) {
        try {
            URL url = new URL(baseUrl + "/" + id);
            return requests.getRequest(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getGroupNotes(String userId) {
        try {
            URL url = new URL(baseUrl + "/user/" + userId);
            return requests.getRequest(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean add(String title, String content, String groupId) throws MalformedURLException {
        if (!validation(title, content, groupId)) {
            throw new IllegalArgumentException();
        }
        HashMap<String, String> object = new HashMap<>();
        object.put("title", title);
        object.put("content", content);
        object.put("userId", groupId);

        ObjectMapper writer = new ObjectMapper();

        try {
            URL url = new URL(baseUrl);
            String json = writer.writeValueAsString(object);
            return requests.postRequest(url, json);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateNote(String id, String title, String content, String groupId) {
        HashMap<String, String> object = new HashMap<>();
        object.put("id", id);
        object.put("title", title);
        object.put("content", content);
        object.put("userId", groupId);

        ObjectMapper writer = new ObjectMapper();
        try {
            URL url = new URL(baseUrl + "/" + id);
            String json = writer.writeValueAsString(object);
            return requests.putRequest(url, json);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteChosenNote(String id) {
        try {
            URL url = new URL(baseUrl + "/" + id);
            return requests.deleteRequest(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteAllNotes() {
        try {
            URL url = new URL(baseUrl);
            return requests.deleteRequest(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteAllGroupNotes(String groupId) {
        try {
            URL url = new URL(baseUrl + "/user/" + groupId);
            return requests.deleteRequest(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean validation(String title, String content, String groupId) {
        //NullPointer
        if (title == null || content == null || groupId == null) {
            return false;
        }
        try {
            Integer.parseInt(groupId);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
