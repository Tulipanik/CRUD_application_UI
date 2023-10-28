package org.example.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.MalformedURLException;
import java.util.HashMap;

public interface NotesServiceInterface {
    String getAllNotes();

    String getTitleNote(String id);

    String getGroupNotes(String userId);

    String add(String title, String content, String groupId) throws MalformedURLException;

    boolean updateNote(String id, String title, String content, String groupId);

    boolean deleteChosenNote(String id);

    boolean deleteAllNotes();

    boolean deleteAllGroupNotes(String groupId);

}
