package org.example.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.MalformedURLException;
import java.util.HashMap;

public class NotesService implements NotesServiceInterface {

    private final HttpRequestServiceInterface requests = new HttpRequestService("http://localhost:8080/notes");

    public String getAllNotes() {
        try {
            return requests.getRequest("");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getIdNote(String id){
        try {
            return requests.getRequest("/" + id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getGroupNotes(String userId) {
        try {
            return requests.getRequest("/user/" + userId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean add(String title, String content, String groupId) throws MalformedURLException {
        if(!validation(title,content,groupId)){
            throw new IllegalArgumentException();
        }
        HashMap<String, String> object = new HashMap<>();
        object.put("title", title);
        object.put("content", content);
        object.put("userId", groupId);

        ObjectMapper writer = new ObjectMapper();

        try {
            String json = writer.writeValueAsString(object);
            return requests.postRequest("", json);
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
            String json = writer.writeValueAsString(object);
            return requests.putRequest("/" + id, json);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteChosenNote(String id) {
        try {
            return requests.deleteRequest("/" + id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteAllNotes() {
        try {
            return requests.deleteRequest("");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteAllGroupNotes(String groupId) {
        try {
            return requests.deleteRequest("/user/" + groupId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean validation(String title, String content, String groupId){
        //NullPointer
        if(title==null || content==null || groupId==null){
            return false;
        }
        try{
            Integer.parseInt(groupId);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
}
