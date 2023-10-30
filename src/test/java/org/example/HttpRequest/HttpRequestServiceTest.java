package org.example.HttpRequest;

import org.example.Services.HttpRequestService;
import org.junit.Assert;
import org.junit.Test;

public class HttpRequestServiceTest {

    @Test
    public void ClassShouldInitialised()
    {
        //Give
        String url="http://normal.url:8000/service";
        //When
        new HttpRequestService(url);
        //Then
        Assert.assertTrue(true);
    }

    @Test(expected = NullPointerException.class)
    public void ClassGetNullString()
    {
        //Give
        String url=null;
        //When
        new HttpRequestService(url);
        //Then
    }

    @Test(expected = IllegalArgumentException.class)
    public void ClassGetEmptyString()
    {
        //Give
        String url="";
        //When
        new HttpRequestService(url);
        //Then
    }

    @Test(expected = IllegalArgumentException.class)
    public void UrlIsNotValid()
    {
        //Give
        String url="http://normal.url:8000/    se    r  vic    $$$#e";
        //When
        new HttpRequestService(url);
        //Then
    }

}
