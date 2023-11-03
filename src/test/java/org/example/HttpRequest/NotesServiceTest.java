package org.example.HttpRequest;

import org.example.Services.NotesService;
import org.junit.Assert;
import org.junit.Test;

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
