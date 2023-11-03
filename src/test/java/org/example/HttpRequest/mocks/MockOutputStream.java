package org.example.HttpRequest.mocks;

import java.io.IOException;
import java.io.OutputStream;

public class MockOutputStream extends OutputStream {

    private final StringBuilder stringBuilder = new StringBuilder();
    @Override
    public void write(int b) throws IOException {
        stringBuilder.append((char) b);
    }

    public String getContent() {
        return stringBuilder.toString();
    }
}
