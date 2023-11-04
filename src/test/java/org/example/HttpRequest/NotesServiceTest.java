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
        String title1 = "Title1";
        String groupId1 = "0";
        String content1 = "Simple note text 1";
        String title2 = "Title2";
        String groupId2 = "1";
        String content2 = "Simple note text 2";
        // When
        notesService.add(title1, content1, groupId1);
        notesService.add(title2, content2, groupId2);
        notesService.deleteAllNotes();
        String response = notesService.getAllNotes();
        //Then
        Assert.assertEquals("[]", response);
    }

    @Test
    public void DeleteNotesGroupByCorrectId() throws Exception {
        //Given
        NotesService notesService = new NotesService();
        String title1 = "Title1";
        String groupId1 = "0";
        String content1 = "Simple note text 1";
        String title2 = "Title2";
        String groupId2 = "1";
        String content2 = "Simple note text 2";
        // When
        notesService.deleteAllNotes();
        notesService.add(title1, content1, groupId1);
        notesService.add(title2, content2, groupId2);
        notesService.deleteAllGroupNotes(groupId1);
        String response1 = notesService.getGroupNotes(groupId1);
        String response2 = notesService.getGroupNotes(groupId2);
        //Then
        String pattern = "\\{\"id\":\"[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}\",\"title\":\"Title2\",\"content\":\"Simple note text 2\",\"userId\":\"1\"\\}";
        Pattern regexPattern = Pattern.compile(pattern);
        Matcher matcher = regexPattern.matcher(response2);
        Assert.assertEquals("[]", response1);
        Assert.assertTrue(matcher.find());
    }

    @Test
    public void DeleteNotesGroupByIncorrectId() throws Exception {
        //Given
        NotesService notesService = new NotesService();
        String title1 = "Title1";
        String groupId1 = "0";
        String content1 = "Simple note text 1";
        String title2 = "Title2";
        String groupId2 = "1";
        String content2 = "Simple note text 2";
        // When
        notesService.deleteAllNotes();
        notesService.add(title1, content1, groupId1);
        notesService.add(title2, content2, groupId2);
        notesService.deleteAllGroupNotes("2");
        String response1 = notesService.getGroupNotes(groupId1);
        String response2 = notesService.getGroupNotes(groupId2);
        //Then
        Assert.assertNotEquals("[]", response1);
    }

    @Test
    public void DeleteNotesGroupByIncorrectIdPerm1() throws Exception {
        //Given
        NotesService notesService = new NotesService();
        String title1 = "Title1";
        String groupId1 = "0";
        String content1 = "Simple note text 1";
        // When
        notesService.deleteAllNotes();
        notesService.add(title1, content1, groupId1);
        notesService.deleteAllGroupNotes("X");
        String response1 = notesService.getGroupNotes(groupId1);
        //Then
        Assert.assertNotEquals("[]", response1);
    }

    @Test
    public void DeleteNoteByCorrectId() throws Exception {
        //Given
        NotesService notesService = new NotesService();
        String title1 = "Title1";
        String groupId1 = "0";
        String content1 = "Simple note text 1";
        String title2 = "Title2";
        String groupId2 = "1";
        String content2 = "Simple note text 2";
        //When
        notesService.deleteAllNotes();
        notesService.add(title1, content1, groupId1);
        notesService.add(title2, content2, groupId2);
        String response = notesService.getAllNotes();
        String pattern = "([0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12})";
        Pattern regexPattern = Pattern.compile(pattern);
        Matcher matcher = regexPattern.matcher(response);
        boolean patternFound = matcher.find();
        String idValue = matcher.group(0);
        notesService.deleteChosenNote(idValue);
        String deletedResponse = notesService.getIdNote(idValue);
        //Then
        Assert.assertTrue(patternFound);
        Assert.assertEquals("", deletedResponse);
    }

    @Test
    public void DeleteNoteByIncorrectId() throws Exception {
        //Given
        NotesService notesService = new NotesService();
        String title1 = "Title1";
        String groupId1 = "0";
        String content1 = "Simple note text 1";
        String title2 = "Title2";
        String groupId2 = "1";
        String content2 = "Simple note text 2";
        //When
        notesService.deleteAllNotes();
        notesService.add(title1, content1, groupId1);
        notesService.add(title2, content2, groupId2);
        String response = notesService.getAllNotes();
        String pattern = "([0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12})";
        Pattern regexPattern = Pattern.compile(pattern);
        Matcher matcher = regexPattern.matcher(response);
        boolean patternFound = matcher.find();
        String idValue = matcher.group(0);
        notesService.deleteChosenNote("incorrectid");
        String deletedResponse = notesService.getIdNote(idValue);
        //Then
        Assert.assertTrue(patternFound);
        Assert.assertNotEquals("", deletedResponse);
    }
    //Reading method tests
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

    @Test
    public void GetNoteByCorrectId() throws Exception {
        //Given
        NotesService notesService = new NotesService();
        String title = "CorrectIdNote";
        String groupId = "5";
        String content = "Simple note text";
        //When
        notesService.deleteAllNotes();
        notesService.add(title, content, groupId);
        String pattern = "([0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12})";
        String response = notesService.getAllNotes();
        Pattern regexPattern = Pattern.compile(pattern);
        Matcher matcher = regexPattern.matcher(response);
        matcher.find();
        String idValue = matcher.group(0);
        String foundNote = notesService.getIdNote(idValue);
        String expectedNote = "{\"id\":\"" + idValue + "\",\"title\":\"IncorrectIdNote\",\"content\":\"Simple note text\",\"userId\":\"5\"}";
        //Then
        Assert.assertEquals(expectedNote, foundNote);
    }

    @Test
    public void GetNoteByIncorrectId() throws Exception {
        //Given
        NotesService notesService = new NotesService();
        String title = "IncorrectIdNote";
        String groupId = "5";
        String content = "Simple note text";
        //When
        notesService.deleteAllNotes();
        notesService.add(title, content, groupId);
        String foundNote = notesService.getIdNote("incorrect id");
        //Then
        Assert.assertEquals("", foundNote);
    }
    //Update method tests
    @Test
    public void UpdateNoteByCorrectIdAndCorrectGroupId() throws Exception {
        //Given
        NotesService notesService = new NotesService();
        String title = "CorrectIdNote";
        String groupId = "5";
        String content = "Simple note text";
        String updatedTitle = "UpdatedNote";
        String updatedGroupId = "0";
        String updatedContent = "Updated note text";
        //When
        notesService.deleteAllNotes();
        notesService.add(title, content, groupId);
        String response = notesService.getGroupNotes(groupId);

        String pattern = "([0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12})";
        Pattern regexPattern = Pattern.compile(pattern);
        Matcher matcher = regexPattern.matcher(response);
        matcher.find();
        String idValue = matcher.group(0);
        notesService.updateNote(idValue, updatedTitle, updatedContent, updatedGroupId);
        String updatedResponse = notesService.getIdNote(idValue);
        String expectedResponse = "{\"id\":\"" + idValue +"\",\"title\":\"UpdatedNote\",\"content\":\"Updated note text\",\"userId\":\"0\"}";
        //Then
        Assert.assertEquals(expectedResponse, updatedResponse);
    }

    @Test
    public void UpdateNoteByCorrectIdAndIncorrectGroupId() throws Exception {
        //Given
        NotesService notesService = new NotesService();
        String title = "CorrectIdNote";
        String groupId = "5";
        String content = "Simple note text";
        String updatedTitle = "UpdatedNote";
        String updatedGroupId = "X";
        String updatedContent = "Updated note text";
        //When
        notesService.deleteAllNotes();
        notesService.add(title, content, groupId);
        String response = notesService.getGroupNotes(groupId);

        String pattern = "([0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12})";
        Pattern regexPattern = Pattern.compile(pattern);
        Matcher matcher = regexPattern.matcher(response);
        matcher.find();
        String idValue = matcher.group(0);
        notesService.updateNote(idValue, updatedTitle, updatedContent, updatedGroupId);
        String updatedResponse = notesService.getIdNote(idValue);
        String expectedResponse = "{\"id\":\"" + idValue +"\",\"title\":\"UpdatedNote\",\"content\":\"Updated note text\",\"userId\":\"X\"}";
        //Then
        Assert.assertEquals(expectedResponse, updatedResponse);
    }

    @Test
    public void UpdateNoteByIncorrectIdAndCorrectGroupId() throws Exception {
        //Given
        NotesService notesService = new NotesService();
        String title = "CorrectIdNote";
        String groupId = "5";
        String content = "Simple note text";
        String updatedTitle = "UpdatedNote";
        String updatedGroupId = "2";
        String updatedContent = "Updated note text";
        //When
        notesService.deleteAllNotes();
        notesService.add(title, content, groupId);
        boolean isSuccessfull = notesService.updateNote("incorrectId", updatedTitle, updatedContent, updatedGroupId);
        //Then
        Assert.assertFalse(isSuccessfull);
    }

    @Test
    public void UpdateNoteByIncorrectIdAndInorrectGroupId() throws Exception {
        //Given
        NotesService notesService = new NotesService();
        String title = "CorrectIdNote";
        String groupId = "5";
        String content = "Simple note text";
        String updatedTitle = "UpdatedNote";
        String updatedGroupId = "X";
        String updatedContent = "Updated note text";
        //When
        notesService.deleteAllNotes();
        notesService.add(title, content, groupId);
        boolean isSuccessfull = notesService.updateNote("incorrectId", updatedTitle, updatedContent, updatedGroupId);
        //Then
        Assert.assertFalse(isSuccessfull);
    }
    //Create method tests

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
