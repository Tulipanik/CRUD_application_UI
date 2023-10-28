package org.example.Services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public interface HttpRequestServiceInterface {
    String getRequest(String endpoint);

    String postRequest(String endpoint, String json);

    boolean putRequest(String endpoint, String json);

    boolean deleteRequest(String endpoint);
}
