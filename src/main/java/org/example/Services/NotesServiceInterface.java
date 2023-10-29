package org.example.Services;

import java.net.MalformedURLException;
import java.util.HashMap;

public interface NotesServiceInterface {
    String getAllNotes();

    String getIdNote(String id);

    String getGroupNotes(String userId);

    boolean add(String title, String content, String groupId) throws MalformedURLException;

    boolean updateNote(String id, String title, String content, String groupId);

    boolean deleteChosenNote(String id);

    boolean deleteAllNotes();

    boolean deleteAllGroupNotes(String groupId);

}
