package org.example.HttpRequest.mocks;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MockHttpURLConnection extends HttpURLConnection {

    MockOutputStream outputStream = new MockOutputStream();
    InputStream inputStream;

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
    public void setRequestProperty(String key, String value) {
        ;;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return outputStream;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return inputStream;
    }

    public void putDataToOutput(String data) throws IOException {
        inputStream = IOUtils.toInputStream(data, "UTF-8");
    }

    public MockOutputStream getMockOutputStream() {
        return outputStream;
    }
}
