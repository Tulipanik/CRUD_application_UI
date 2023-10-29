package org.example.Services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public interface HttpRequestServiceInterface {
    String getRequest(String endpoint) throws ProtocolException, Exception;

    boolean postRequest(String endpoint, String json) throws MalformedURLException, Exception;

    boolean putRequest(String endpoint, String json) throws Exception;

    boolean deleteRequest(String endpoint) throws Exception;
}
