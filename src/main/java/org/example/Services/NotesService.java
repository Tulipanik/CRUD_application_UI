package org.example.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.MalformedURLException;
import java.util.HashMap;

public class NotesService implements NotesServiceInterface {

    private final HttpRequestServiceInterface requests = new HttpRequestService("http://localhost:8080/notes");

    public String getAllNotes() {
        return requests.getRequest("");
    }

    public String getTitleNote(String id) {
        return requests.getRequest(id);
    }

    public String getGroupNotes(String userId) {
        return requests.getRequest("/user/" + userId);
    }

    public String add(String title, String content, String groupId) throws MalformedURLException {
        HashMap<String, String> object = new HashMap<>();
        object.put("title", title);
        object.put("content", content);
        object.put("userId", groupId);

        ObjectMapper writer = new ObjectMapper();
        try {
            String json = writer.writeValueAsString(object);
            return requests.postRequest("", json);
        } catch (JsonProcessingException e) {
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
            String json = writer.writeValueAsString(object);
            return requests.putRequest("/" + id, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteChosenNote(String id) {
        return requests.deleteRequest("/" + id);
    }

    public boolean deleteAllNotes() {
        return requests.deleteRequest("");
    }

    public boolean deleteAllGroupNotes(String groupId) {
        return requests.deleteRequest("/user/" + groupId);
    }

}
