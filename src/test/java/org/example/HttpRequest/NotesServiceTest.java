package org.example.HttpRequest;

import org.example.Services.NotesService;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NotesServiceTest {

    //Class initialisation test

    @Test
    public void ClassShouldInitialised() {
        //Give
        //When
        new NotesService();
        //Then
        Assert.assertTrue(true);
    }

    //CRUD method tests
    //Deleting method tests
    @Test
    public void DeleteAllNotes() throws Exception {
        //Given
        NotesService notesService = new NotesService();
        String title = "Title";
        String groupId = "0";
        String content = "Simple note text";
        // When
        notesService.add(title, content, groupId);
        notesService.deleteAllNotes();
        String response = notesService.getAllNotes();
        //Then
        Assert.assertEquals("[]", response);
    }
    //Getting method tests
    @Test
    public void GetAllNotes() throws Exception {
        //Given
        NotesService notesService = new NotesService();
        String title = "Title";
        String groupId = "0";
        String content = "Simple note text";
        // When
        notesService.deleteAllNotes();
        notesService.add(title, content, groupId);
        String response = notesService.getAllNotes();
        // Then
        Assert.assertNotNull(response);
        String pattern = "\\{\"id\":\"[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}\",\"title\":\"Title\",\"content\":\"Simple note text\",\"userId\":\"0\"\\}";
        Pattern regexPattern = Pattern.compile(pattern);
        Matcher matcher = regexPattern.matcher(response);
        Assert.assertTrue(matcher.find());
    }

    @Test
    public void GetNoteByCorrectGroupId() throws Exception {
        //Given
        NotesService notesService = new NotesService();
        String title = "CorrectIdNote";
        String groupId = "5";
        String content = "Simple note text";
        //When
        notesService.deleteAllNotes();
        notesService.add(title, content, groupId);
        String response = notesService.getGroupNotes(groupId);
        //Then
        Assert.assertNotNull(response);
        String pattern = "\\{\"id\":\"[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}\",\"title\":\"CorrectIdNote\",\"content\":\"Simple note text\",\"userId\":\"5\"\\}";
        Pattern regexPattern = Pattern.compile(pattern);
        Matcher matcher = regexPattern.matcher(response);
        Assert.assertTrue(matcher.find());
    }

    @Test
    public void GetNoteByIncorrectGroupId() throws Exception {
        //Given
        NotesService notesService = new NotesService();
        String title = "IncorrectIdNote";
        String groupId = "5";
        String content = "Simple note text";
        String incorrectGroupId = "X";
        //When
        notesService.deleteAllNotes();
        notesService.add(title, content, groupId);
        String response = notesService.getGroupNotes(incorrectGroupId);
        //Then
        Assert.assertEquals("[]", response);
    }


    //Adding method tests

    @Test
    public void AddCorrectData() throws Exception {
        //Give
        NotesService notesService = new NotesService();
        String title = "Title";
        String groupId = "0";
        String content = "Simple note text";
        //When
        boolean response = notesService.add(title, content, groupId);
        //Then
        Assert.assertTrue(response);
    }

    @Test(expected = IllegalArgumentException.class)
    public void AddCorrectDataButPerm1() throws Exception {
        //Give
        NotesService notesService = new NotesService();
        String title = "Simple note text";
        String groupId = "Title";
        String content = "0";
        //When
        boolean response = notesService.add(title, content, groupId);
        //Then
    }

    @Test(expected = IllegalArgumentException.class)
    public void AddCorrectDataButPerm2() throws Exception {
        //Give
        NotesService notesService = new NotesService();
        String title = "0";
        String groupId = "Simple note text";
        String content = "Title";
        //When
        boolean response = notesService.add(title, content, groupId);
        //Then
    }

    @Test(expected = IllegalArgumentException.class)
    public void AddNullOneDataPerm1() throws Exception {
        //Give
        NotesService notesService = new NotesService();
        String title = null;
        String groupId = "0";
        String content = "Simple note text";
        //When
        notesService.add(title, content, groupId);
        //Then
    }

    @Test(expected = IllegalArgumentException.class)
    public void AddNullOneDataPerm2() throws Exception {
        //Give
        NotesService notesService = new NotesService();
        String title = "Title";
        String groupId = null;
        String content = "Simple note text";
        //When
        notesService.add(title, content, groupId);
        //Then
    }

    @Test(expected = IllegalArgumentException.class)
    public void AddNullOneDataPerm3() throws Exception {
        //Give
        NotesService notesService = new NotesService();
        String title = "Title";
        String groupId = "0";
        String content = null;
        //When
        notesService.add(title, content, groupId);
        //Then
    }

    @Test(expected = IllegalArgumentException.class)
    public void AddNullTwoDataPerm1() throws Exception {
        //Give
        NotesService notesService = new NotesService();
        String title = null;
        String groupId = null;
        String content = "Simple note text";
        //When
        notesService.add(title, content, groupId);
        //Then
    }

    @Test(expected = IllegalArgumentException.class)
    public void AddNullTwoDataPerm2() throws Exception {
        //Give
        NotesService notesService = new NotesService();
        String title = "Title";
        String groupId = null;
        String content = null;
        //When
        notesService.add(title, content, groupId);
        //Then
    }

    @Test(expected = IllegalArgumentException.class)
    public void AddNullTwoDataPerm3() throws Exception {
        //Give
        NotesService notesService = new NotesService();
        String title = null;
        String groupId = "0";
        String content = null;
        //When
        notesService.add(title, content, groupId);
        //Then
    }

    @Test(expected = IllegalArgumentException.class)
    public void AddNullThreeData() throws Exception {
        //Give
        NotesService notesService = new NotesService();
        String title = null;
        String groupId = null;
        String content = null;
        //When
        notesService.add(title, content, groupId);
        //Then
    }

    @Test(expected = IllegalArgumentException.class)
    public void AddInCorrectGroupIdDataPerm1() throws Exception {
        //Give
        NotesService notesService = new NotesService();
        String title = "Title";
        String groupId = "X";
        String content = "Simple note text";
        //When
        notesService.add(title, content, groupId);
        //Then
    }

    @Test(expected = IllegalArgumentException.class)
    public void AddInCorrectGroupIdDataPerm2() throws Exception {
        //Give
        NotesService notesService = new NotesService();
        String title = null;
        String groupId = "X";
        String content = "Simple note text";
        //When
        notesService.add(title, content, groupId);
        //Then
    }

    @Test(expected = IllegalArgumentException.class)
    public void AddInCorrectGroupIdDataPerm3() throws Exception {
        //Give
        NotesService notesService = new NotesService();
        String title = "Title";
        String groupId = "X";
        String content = null;
        //When
        notesService.add(title, content, groupId);
        //Then
    }

    @Test(expected = IllegalArgumentException.class)
    public void AddInCorrectGroupIdDataPerm4() throws Exception {
        //Give
        NotesService notesService = new NotesService();
        String title = null;
        String groupId = "X";
        String content = null;
        //When
        notesService.add(title, content, groupId);
        //Then
    }
}
