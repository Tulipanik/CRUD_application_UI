package org.example.HttpRequest;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.example.Services.HttpRequestService;
import org.example.Services.NotesService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@RunWith(JUnitParamsRunner.class)
public class NotesServiceTest {

    //Static data
    private String baseUrl = "http://localhost:8080/notes";
    private String title1 = "Title1";
    private String groupId1 = "0";
    private String content1 = "Simple note text 1";
    private String title2 = "Title2";
    private String groupId2 = "1";
    private String content2 = "Simple note text 2";
    private String incorrectNumberId ="2";
    private String incorrectCharId ="X";
    private String jsonAdd="{\"title\":\"Title1\",\"userId\":\"0\",\"content\":\"Simple note text 1\"}";
    private String jsonEmptyResponse="[]";
    private String jsonResponseId1 ="[{\"id\":\"11dc6a40-cd3a-49c2-9ae2-1fa13657ae8c\",\"title\":\"Title1\",\"content\":\"Simple note text 1\",\"userId\":\"0\"}]";
    private String noteId1="11dc6a40-cd3a-49c2-9ae2-1fa13657ae8c";
    private String jsonResponseId2 ="[{\"id\":\"22dc6a40-cd3a-49c1-9ae1-2fa23657ae8c\",\"title\":\"Title2\",\"content\":\"Simple note text 2\",\"userId\":\"1\"}]";
    private String noteId2="22dc6a40-cd3a-49c1-9ae1-2fa23657ae8c";
    private String jsonAllResponse="[{\"id\":\"11dc6a40-cd3a-49c2-9ae2-1fa13657ae8c\",\"title\":\"Title1\",\"content\":\"Simple note text 1\",\"userId\":\"0\"},{\"id\":\"22dc6a40-cd3a-49c1-9ae1-2fa23657ae8c\",\"title\":\"Title2\",\"content\":\"Simple note text 2\",\"userId\":\"1\"}]";
    private String jsonIncorrectResponse="[{\"id\":\""+incorrectNumberId+"\",\"title\":\"Title1\",\"content\":\"Simple note text 1\",\"userId\":\"0\"}]";
    private String patternForId2 = "\\{\"id\":\"[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}\",\"title\":\"Title2\",\"content\":\"Simple note text 2\",\"userId\":\"1\"\\}";
    private String idPattern = "([0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12})";

    //Initialised data
    private URL url;
    private URL urlGroup1;
    private URL urlNote1;
    private URL urlGroup2;
    private URL urlNote2;
    private URL urlIncorrectUserNumberId;
    private URL urlIncorrectUserCharId;
    private URL urlIncorrectNoteId;

    @Before
    public void setup() throws MalformedURLException {
        this.url=new URL(baseUrl);
        this.urlGroup1 =new URL(url+ "/user/" + groupId1);
        this.urlGroup2 =new URL(url+ "/user/" + groupId2);
        this.urlIncorrectUserNumberId =new URL(url+ "/user/" + incorrectNumberId);
        this.urlIncorrectUserCharId =new URL(url+ "/user/" + incorrectCharId);
        this.urlNote1 =new URL(url+ "/" + noteId1);
        this.urlNote2 =new URL(url+ "/" + noteId2);
        this.urlIncorrectNoteId=new URL(url+ "/" + incorrectNumberId);
    }

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
        //Mock
        HttpRequestService requestService=mock(HttpRequestService.class);
        when(requestService.deleteRequest(url)).thenReturn(true);
        when(requestService.getRequest(url)).thenReturn(jsonEmptyResponse);

        NotesService notesService = new NotesService(requestService);
        // When
        notesService.add(title1, content1, groupId1);
        notesService.add(title2, content2, groupId2);
        notesService.deleteAllNotes();
        String response = notesService.getAllNotes();
        //Then
        Assert.assertEquals(jsonEmptyResponse, response);
    }

    @Test
    public void DeleteNotesGroupByCorrectId() throws Exception {
        //Given
        //Mock
        HttpRequestService requestService=mock(HttpRequestService.class);
        when(requestService.deleteRequest(urlGroup1)).thenReturn(true);
        when(requestService.getRequest(urlGroup1)).thenReturn(jsonEmptyResponse);
        when(requestService.getRequest(urlGroup2)).thenReturn(jsonResponseId2);

        NotesService notesService = new NotesService(requestService);
        // When
        notesService.add(title1, content1, groupId1);
        notesService.add(title2, content2, groupId2);

        notesService.deleteAllGroupNotes(groupId1);

        String response1 = notesService.getGroupNotes(groupId1);
        String response2 = notesService.getGroupNotes(groupId2);
        //Then
        Pattern regexPattern = Pattern.compile(patternForId2);
        Matcher matcher = regexPattern.matcher(response2);
        Assert.assertEquals(jsonEmptyResponse, response1);
        Assert.assertTrue(matcher.find());
    }

    @Test
    public void DeleteNotesGroupByIncorrectId() throws Exception {
        //Given
        //Mock
        HttpRequestService requestService=mock(HttpRequestService.class);
        when(requestService.deleteRequest(urlIncorrectUserNumberId)).thenReturn(false);
        when(requestService.getRequest(urlGroup1)).thenReturn(jsonResponseId1);
        when(requestService.getRequest(urlGroup2)).thenReturn(jsonResponseId2);

        NotesService notesService = new NotesService(requestService);
        // When

        notesService.add(title1, content1, groupId1);
        notesService.add(title2, content2, groupId2);

        boolean deleted=notesService.deleteAllGroupNotes(incorrectNumberId);

        String response1 = notesService.getGroupNotes(groupId1);
        String response2 = notesService.getGroupNotes(groupId2);
        //Then
        Assert.assertFalse(deleted);
        Assert.assertEquals(jsonResponseId1, response1);
        Assert.assertEquals(jsonResponseId2, response2);
    }

    @Test
    public void DeleteNotesGroupByIncorrectIdPerm1() throws Exception {
        //Given
        //Mock
        HttpRequestService requestService=mock(HttpRequestService.class);
        when(requestService.deleteRequest(urlIncorrectUserCharId)).thenReturn(false);
        when(requestService.getRequest(urlGroup1)).thenReturn(jsonResponseId1);

        NotesService notesService = new NotesService(requestService);

        // When
        notesService.add(title1, content1, groupId1);
        notesService.deleteAllGroupNotes(incorrectCharId);
        String response1 = notesService.getGroupNotes(groupId1);
        //Then
        Assert.assertNotEquals(jsonEmptyResponse, response1);
    }

    @Test
    public void DeleteNoteByCorrectId() throws Exception {
        //Given
        //Mock
        HttpRequestService requestService=mock(HttpRequestService.class);
        when(requestService.deleteRequest(urlNote1)).thenReturn(true);
        when(requestService.getRequest(urlNote1)).thenReturn("");
        when(requestService.getRequest(url)).thenReturn(jsonAllResponse);
        when(requestService.getRequest(urlGroup1)).thenReturn(jsonResponseId1);
        when(requestService.getRequest(urlGroup1)).thenReturn(jsonResponseId1);

        NotesService notesService = new NotesService(requestService);

        //When
        notesService.add(title1, content1, groupId1);
        notesService.add(title2, content2, groupId2);

        String response = notesService.getAllNotes();
        Pattern regexPattern = Pattern.compile(idPattern);
        Matcher matcher = regexPattern.matcher(response);
        boolean patternFound = matcher.find();
        
        String idValue = matcher.group(0);
        boolean deleted=notesService.deleteChosenNote(idValue);
        String deletedResponse = notesService.getIdNote(idValue);
        //Then
        Assert.assertTrue(patternFound);
        Assert.assertTrue(deleted);
        Assert.assertEquals("", deletedResponse);
    }

    @Test
    public void DeleteNoteByIncorrectId() throws Exception {
        //Given
        //Mock
        HttpRequestService requestService=mock(HttpRequestService.class);
        when(requestService.deleteRequest(urlIncorrectNoteId)).thenReturn(false);
        when(requestService.getRequest(urlNote1)).thenReturn(jsonResponseId1);
        when(requestService.getRequest(url)).thenReturn(jsonAllResponse);
        when(requestService.getRequest(urlGroup1)).thenReturn(jsonResponseId1);
        when(requestService.getRequest(urlGroup1)).thenReturn(jsonResponseId1);

        NotesService notesService = new NotesService(requestService);

        //When
        notesService.add(title1, content1, groupId1);
        notesService.add(title2, content2, groupId2);

        String response = notesService.getAllNotes();
        Pattern regexPattern = Pattern.compile(idPattern);
        Matcher matcher = regexPattern.matcher(response);
        boolean patternFound = matcher.find();

        String idValue = matcher.group(0);
        boolean deleted=notesService.deleteChosenNote(incorrectNumberId);
        String deletedResponse = notesService.getIdNote(idValue);
        //Then
        Assert.assertTrue(patternFound);
        Assert.assertFalse(deleted);
        Assert.assertNotEquals("", deletedResponse);
    }
    //Reading method tests
    @Test
    public void GetAllNotes() throws Exception {
        //Given
        //Mock
        HttpRequestService requestService=mock(HttpRequestService.class);
        when(requestService.getRequest(url)).thenReturn("["+ jsonResponseId2 +"]");

        NotesService notesService = new NotesService(requestService);
        // When

        notesService.add(title2, content2, groupId2);
        String response = notesService.getAllNotes();
        // Then
        Assert.assertNotNull(response);
        Pattern regexPattern = Pattern.compile(patternForId2);
        Matcher matcher = regexPattern.matcher(response);
        Assert.assertTrue(matcher.find());
    }

    @Test
    public void GetNoteByCorrectGroupId() throws Exception {
        //Given
        //Mock
        HttpRequestService requestService=mock(HttpRequestService.class);
        when(requestService.getRequest(urlGroup2)).thenReturn("["+ jsonResponseId2 +"]");

        NotesService notesService = new NotesService(requestService);

        //When
        notesService.add(title2, content2, groupId2);
        String response = notesService.getGroupNotes(groupId2);
        //Then
        Pattern regexPattern = Pattern.compile(patternForId2);
        Matcher matcher = regexPattern.matcher(response);
        Assert.assertTrue(matcher.find());
    }

    @Test
    public void GetNoteByIncorrectGroupId() throws Exception {
        //Given
        //Mock
        HttpRequestService requestService=mock(HttpRequestService.class);
        when(requestService.getRequest(urlIncorrectUserNumberId)).thenReturn("[]");

        NotesService notesService = new NotesService(requestService);
        //When
        notesService.add(title2, content2, groupId2);
        String response = notesService.getGroupNotes(incorrectNumberId);
        //Then
        Assert.assertEquals("[]", response);
    }

    @Test
    public void GetNoteByCorrectId() throws Exception {
        //Given
        //Mock
        HttpRequestService requestService=mock(HttpRequestService.class);
        when(requestService.getRequest(url)).thenReturn("["+ jsonResponseId2 +"]");
        when(requestService.getRequest(urlNote2)).thenReturn(jsonResponseId2);

        NotesService notesService = new NotesService(requestService);
        //When
        notesService.add(title2, content2, groupId2);

        String response = notesService.getAllNotes();

        Pattern regexPattern = Pattern.compile(idPattern);
        Matcher matcher = regexPattern.matcher(response);
        boolean patternFound=matcher.find();

        String idValue = matcher.group(0);
        String foundNote = notesService.getIdNote(idValue);

        //Then
        Assert.assertTrue(patternFound);
        Assert.assertEquals(jsonResponseId2, foundNote);
    }

    @Test
    public void GetNoteByIncorrectId() throws Exception {
        //Given
        //Mock
        HttpRequestService requestService=mock(HttpRequestService.class);
        when(requestService.getRequest(urlIncorrectNoteId)).thenReturn("");

        NotesService notesService = new NotesService(requestService);

        //When
        notesService.add(title2, content2, groupId2);
        String foundNote = notesService.getIdNote(incorrectNumberId);
        //Then
        Assert.assertEquals("", foundNote);
    }
    //Update method tests
    @Test
    public void UpdateNoteByCorrectIdAndCorrectGroupId() throws Exception {
        //Given
        //Mock
        HttpRequestService requestService=mock(HttpRequestService.class);
        when(requestService.getRequest(urlGroup1)).thenReturn("["+ jsonResponseId1 +"]");

        NotesService notesService = new NotesService(requestService);
        //When
        notesService.add(title1, content1, groupId1);
        String response = notesService.getGroupNotes(groupId1);

        Pattern regexPattern = Pattern.compile(idPattern);
        Matcher matcher = regexPattern.matcher(response);
        boolean patternFound=matcher.find();

        String idValue = matcher.group(0);
        notesService.updateNote(idValue, title2, content2, groupId2);

        //Update Mock
        when(requestService.getRequest(urlNote1)).thenReturn(jsonResponseId2);

        String updatedResponse = notesService.getIdNote(idValue);
        //Then
        Assert.assertTrue(patternFound);
        Assert.assertEquals(jsonResponseId2, updatedResponse);
    }

    @Test
    public void UpdateNoteByIncorrectIdAndCorrectGroupId() throws Exception {
        //Given
        //Mock
        HttpRequestService requestService=mock(HttpRequestService.class);
        when(requestService.getRequest(urlGroup1)).thenReturn("["+ jsonResponseId1 +"]");
        when(requestService.putRequest(urlIncorrectNoteId,jsonIncorrectResponse)).thenReturn(false);

        NotesService notesService = new NotesService(requestService);

        //When
        notesService.add(title1, content1, groupId1);
        boolean isSuccessfull = notesService.updateNote(incorrectNumberId, title2, content2, groupId2);
        //Then
        Assert.assertFalse(isSuccessfull);
    }
    //Create method tests

    @Test
    public void AddCorrectData() throws Exception {
        //Give
        //Mock
        URL url=new URL(baseUrl);
        HttpRequestService requestService=mock(HttpRequestService.class);
        when(requestService.postRequest(url,jsonAdd)).thenReturn(true);
        NotesService notesService = new NotesService(requestService);
        //When
        boolean response = notesService.add(title1, content1, groupId1);
        //Then
        Assert.assertTrue(response);
    }

    @Test
    @Parameters(method = "AddCorrectDataButPermParameters")
    public void AddCorrectDataButPerm(String json,String title,String groupId,String content) throws Exception {
        //Give
        //Mock
        URL url=new URL(baseUrl);
        HttpRequestService requestService=mock(HttpRequestService.class);
        when(requestService.postRequest(url,json)).thenReturn(true);
        NotesService notesService = new NotesService(requestService);

        //When
        boolean response = notesService.add(title, content, groupId);
        //Then
        Assert.assertTrue(response);
    }

    private Object[] AddCorrectDataButPermParameters() {
        return new Object[]{
                new Object[]{"{\"title\":\"Simple note text\",\"userId\":\"Title\",\"content\":\"0\"}", "Simple note text", "Title", "0"},
                new Object[]{"{\"title\":\"0\",\"userId\":\"Simple note text\",\"content\":\"Title\"}", "0", "Simple note text", "Title"}
        };
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters(method ="AddNullDataPermParameters")
    public void AddNullDataPerm(String title,String groupId,String content) throws Exception {
        //Give
        NotesService notesService = new NotesService();
        //When
        notesService.add(title, content, groupId);
        //Then
    }

    private Object[] AddNullDataPermParameters() {
        return new Object[]{
                new Object[]{null,"0","Simple note text"},
                new Object[]{"Title",null,"Simple note text"},
                new Object[]{"Title","0",null},
                new Object[]{null,null,"Simple note text"},
                new Object[]{"Title",null,null},
                new Object[]{null,"0",null},
                new Object[]{null,null,null}
        };
    }
}
