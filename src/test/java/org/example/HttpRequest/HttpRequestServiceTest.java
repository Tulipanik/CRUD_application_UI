package org.example.HttpRequest;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.example.HttpRequest.mocks.MockHttpURLConnection;
import org.example.Services.HttpRequestService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.net.URL;

import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(JUnitParamsRunner.class)
@PrepareForTest({ URL.class })
public class HttpRequestServiceTest {
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
        "201, true",
        "200, false",
        "101, false",
        "404, false",
        "500, false",
    })
    public void whenPerformingPostRequest_thenCorrectOutputIsReturned(int responseCode, boolean expectedOutput) throws Exception {
        // given
        var connectionMock = new MockHttpURLConnection(responseCode);

        URL urlMock = mock(URL.class);
        when(urlMock.openConnection()).thenReturn(connectionMock);
        when(urlMock.toString()).thenReturn("http://localhost");
        when(urlMock.getProtocol()).thenReturn("https");

        HttpRequestService httpRequestService = new HttpRequestService();

        String data = "{\"title\":\"Title\",\"userId\":\"0\",\"content\":\"Simple note text\"}";

        // when
        boolean response = httpRequestService.postRequest(urlMock, data);

        // then
        Assert.assertEquals(expectedOutput, response);
        Assert.assertTrue(connectionMock.getDoInput());
        Assert.assertTrue(connectionMock.getDoOutput());
        Assert.assertEquals(HttpRequestService.POST_Method, connectionMock.getRequestMethod());
        Assert.assertEquals(data, connectionMock.getMockOutputStream().getContent());
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
        boolean response = httpRequestService.deleteRequest(urlMock);

        // then
        Assert.assertEquals(expectedOutput, response);
        Assert.assertEquals(HttpRequestService.DELETE_Method, connectionMock.getRequestMethod());
    }

}
