package org.example.HttpRequest.mocks;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MockHttpURLConnection extends HttpURLConnection {

    OutputStream outputStream = new MockOutputStream();

    public MockHttpURLConnection(final int responseCode) throws MalformedURLException {
        super(new URL("http://localhost"));

        this.responseCode = responseCode;
    }

    @Override
    public int getResponseCode() {
        return responseCode;
    }

    @Override
    public void disconnect() {}

    @Override
    public boolean usingProxy() {
        return false;
    }

    @Override
    public void connect() throws IOException {}

    @Override
    public void setRequestMethod(String method) {
        this.method = method;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return outputStream;
    }

    public MockOutputStream getMockOutputStream() {
        return (MockOutputStream) outputStream;
    }
}
