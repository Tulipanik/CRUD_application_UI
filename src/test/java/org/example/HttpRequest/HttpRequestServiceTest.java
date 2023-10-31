package org.example.HttpRequest;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.example.HttpRequest.mocks.MockHttpURLConnection;
import org.example.Services.HttpRequestService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import java.lang.Exception;

import java.net.MalformedURLException;
import java.net.URL;

import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(JUnitParamsRunner.class)
@PrepareForTest({ URL.class })
public class HttpRequestServiceTest {
    private static final String httpsProtocol = "https";
    private static final String correctURL = "https://localhost:1234/notes";
    private static final String wrongURL = "http://localhost.bigappenddix";
    private static final String malformedURL = "://wmalformedurl.com";
    private static final String jsonMockData = "{\"title\":\"Title\",\"userId\":\"0\",\"content\":\"Simple note text\"}";

    @Before
    public static void setup()
    {

    }

    @Test
    public void whenCreatingClassWithNoArguments_newInstanceIsCreated() {
        // given

        // when
        var newInstance = new HttpRequestService();

        // then
        Assert.assertNotNull(newInstance);
        Assert.assertEquals("UTF-8", newInstance.getDefaultCharset());
    }

    @Test
    public void whenCreatingClassWithDefaultCharset_newInstanceIsCreated() {
        // given
        String defaultCharset = "UTF-16";

        // when
        var newInstance = new HttpRequestService(defaultCharset);

        // then
        Assert.assertNotNull(newInstance);
        Assert.assertEquals(defaultCharset, newInstance.getDefaultCharset());
    }

    @Test
    @Parameters({
            "200",
    })
    public void whenPerformingValidGetRequest_thenCorrectOutputIsReturned(int responseCode) throws Exception {
        // given
        var connectionMock = new MockHttpURLConnection(responseCode);

        URL urlMock = mock(URL.class);
        when(urlMock.openConnection()).thenReturn(connectionMock);
        HttpRequestService httpRequestService = new HttpRequestService();

        connectionMock.putDataToOutput(jsonMockData);

        // when
        var response = httpRequestService.getRequest(urlMock);

        // then
        Assert.assertEquals(jsonMockData, response);
        Assert.assertEquals(HttpRequestService.GET_Method, connectionMock.getRequestMethod()); // TODO: Not working
    }

    @Test(expected = Exception.class)
    public void whenPerformingGetRequestWithInvalidURL_thenExceptionIsThrown() throws Exception {
        // given
        URL url = new URL(wrongURL);
        HttpRequestService httpRequestService = new HttpRequestService();

        // when
        var response = httpRequestService.getRequest(url);

        // then
    }

    @Test(expected = MalformedURLException.class)
    public void whenPerformingGetRequestWithMalformedURL_thenExceptionIsThrown() throws Exception {
        // given
        URL url = new URL(malformedURL);
        HttpRequestService httpRequestService = new HttpRequestService();

        // when
        var response = httpRequestService.getRequest(url);

        // then
    }

    @Test
    @Parameters({
            "201, true",
            "200, false",
            "101, false",
            "404, false",
            "500, false",
    })
    public void whenPerformingValidPostRequest_thenCorrectOutputIsReturned(int responseCode, boolean expectedOutput) throws Exception {
        // given
        var connectionMock = new MockHttpURLConnection(responseCode);

        URL urlMock = mock(URL.class);
        when(urlMock.openConnection()).thenReturn(connectionMock);
        when(urlMock.toString()).thenReturn(correctURL);
        when(urlMock.getProtocol()).thenReturn(httpsProtocol);

        HttpRequestService httpRequestService = new HttpRequestService();

        // when
        var response = httpRequestService.postRequest(urlMock, jsonMockData);

        // then
        Assert.assertEquals(expectedOutput, response);
        Assert.assertTrue(connectionMock.getDoInput());
        Assert.assertTrue(connectionMock.getDoOutput());
        Assert.assertEquals(HttpRequestService.POST_Method, connectionMock.getRequestMethod());
        Assert.assertEquals(jsonMockData, connectionMock.getMockOutputStream().getContent());
    }

    @Test(expected = Exception.class)
    public void whenPerformingPostRequestWithInvalidURL_thenExceptionIsThrown() throws Exception {
        // given
        URL url = new URL(wrongURL);
        HttpRequestService httpRequestService = new HttpRequestService();

        // when
        var response = httpRequestService.postRequest(url, jsonMockData);

        // then
    }

    @Test(expected = MalformedURLException.class)
    public void whenPerformingPostRequestWithMalformedURL_thenExceptionIsThrown() throws Exception {
        // given
        URL url = new URL(malformedURL);
        HttpRequestService httpRequestService = new HttpRequestService();

        // when
        var response = httpRequestService.postRequest(url, jsonMockData);

        // then
    }

    @Test
    @Parameters({
        "204, true",
        "200, false",
        "101, false",
        "404, false",
        "500, false",
    })
    public void whenPerformingValidDeleteRequest_thenCorrectResponseIsReturned(final int responseCode, boolean expectedOutput) throws Exception {
        // given
        var connectionMock = new MockHttpURLConnection(responseCode);

        URL urlMock = mock(URL.class);
        when(urlMock.openConnection()).thenReturn(connectionMock);

        HttpRequestService httpRequestService = new HttpRequestService();

        // when
        var response = httpRequestService.deleteRequest(urlMock);

        // then
        Assert.assertEquals(expectedOutput, response);
        Assert.assertEquals(HttpRequestService.DELETE_Method, connectionMock.getRequestMethod());
    }

    @Test(expected = Exception.class)
    public void whenPerformingDeleteRequestWithInvalidURL_thenExceptionIsThrown() throws Exception {
        // given
        URL url = new URL(wrongURL);
        HttpRequestService httpRequestService = new HttpRequestService();

        // when
        var response = httpRequestService.deleteRequest(url);

        // then
    }

    @Test(expected = MalformedURLException.class)
    public void whenPerformingDeleteRequestWithMalformedURL_thenExceptionIsThrown() throws Exception {
        // given
        URL url = new URL(malformedURL);
        HttpRequestService httpRequestService = new HttpRequestService();

        // when
        var response = httpRequestService.deleteRequest(url);

        // then
    }

}
