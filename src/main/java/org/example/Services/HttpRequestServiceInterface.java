package org.example.Services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public interface HttpRequestServiceInterface {
    String getRequest(URL url) throws ProtocolException, Exception;

    boolean postRequest(URL url, String json) throws MalformedURLException, Exception;

    boolean putRequest(URL url, String json) throws Exception;

    boolean deleteRequest(URL url) throws Exception;
}
