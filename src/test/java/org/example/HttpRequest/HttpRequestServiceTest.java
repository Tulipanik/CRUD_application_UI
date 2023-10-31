package org.example.HttpRequest;

import org.example.Services.HttpRequestService;
import org.junit.Assert;
import org.junit.Test;

public class HttpRequestServiceTest {

    //Class initialisation tests

    @Test
    public void ClassShouldInitialised() {
        //Give
        String url = "http://normal.url:8000/service";
        //When
        new HttpRequestService(url);
        //Then
        Assert.assertTrue(true);
    }

    @Test(expected = NullPointerException.class)
    public void ClassGetNullString() {
        //Give
        String url = null;
        //When
        new HttpRequestService(url);
        //Then
    }

    @Test(expected = IllegalArgumentException.class)
    public void ClassGetEmptyString() {
        //Give
        String url = "";
        //When
        new HttpRequestService(url);
        //Then
    }

    @Test(expected = IllegalArgumentException.class)
    public void UrlIsNotValid() {
        //Give
        String url = "http://normal.url:8000/    se    r  vic    $$$#e";
        //When
        new HttpRequestService(url);
        //Then
    }

    //CRUD method tests
    //Adding method tests

    @Test
    public void AddCorrectData() throws Exception {
        //Give
        String url = "http://localhost:8080/notes";
        String endpoint = "";
        String data = "{\"title\":\"Title\",\"userId\":\"0\",\"content\":\"Simple note text\"}";
        HttpRequestService httpRequestService = new HttpRequestService(url);
        //When
        boolean response = httpRequestService.postRequest(endpoint, data);
        //Then
        Assert.assertTrue(response);
    }

    @Test
    public void AddInCorrectData() throws Exception {
        //Give
        String url = "http://localhost:8080/notes";
        String endpoint = "";
        String data = "{\"tit    le\":\"Title\",\"use      rId\":\"0\"}";
        HttpRequestService httpRequestService = new HttpRequestService(url);
        //When
        boolean response = httpRequestService.postRequest(endpoint, data);
        //Then
        Assert.assertFalse(response);
    }

    @Test
    public void AddIncorrectUrl() throws Exception {
        //Give
        String url = "http://l  st:8   /no   tes";
        String endpoint = "";
        String data = "{\"title\":\"Title\",\"userId\":\"0\",\"content\":\"Simple note text\"}";
        HttpRequestService httpRequestService = new HttpRequestService(url);
        //When
        boolean response = httpRequestService.postRequest(endpoint, data);
        //Then
        Assert.assertFalse(response);
    }

    @Test
    public void AddIncorrectEndpoint() throws Exception {
        //Give
        String url = "http://localhost:8080/notes";
        String endpoint = "wrong/endpoint/here";
        String data = "{\"title\":\"Title\",\"userId\":\"0\",\"content\":\"Simple note text\"}";
        HttpRequestService httpRequestService = new HttpRequestService(url);
        //When
        boolean response = httpRequestService.postRequest(endpoint, data);
        //Then
        Assert.assertFalse(response);
    }

    @Test
    public void AddCorrectDataButPermutation1() throws Exception {
        //Give
        String url = "http://localhost:8080/notes";
        String endpoint = "";
        String data = "{\"userId\":\"0\",\"content\":\"Simple note text\",\"title\":\"Title\"}";
        HttpRequestService httpRequestService = new HttpRequestService(url);
        //When
        boolean response = httpRequestService.postRequest(endpoint, data);
        //Then
        Assert.assertTrue(response);
    }

    @Test
    public void AddCorrectDataButPermutation2() throws Exception {
        //Give
        String url = "http://localhost:8080/notes";
        String endpoint = "";
        String data = "{\"content\":\"Simple note text\",\"title\":\"Title\",\"userId\":\"0\"}";
        HttpRequestService httpRequestService = new HttpRequestService(url);
        //When
        boolean response = httpRequestService.postRequest(endpoint, data);
        //Then
        Assert.assertTrue(response);
    }

}
