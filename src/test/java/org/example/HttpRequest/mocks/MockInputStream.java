package org.example.HttpRequest.mocks;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MockInputStream extends InputStream {
    private final StringBuilder stringBuilder = new StringBuilder();
    private int index = 0;

    @Override
    public int read() throws IOException {
        var val = stringBuilder.toString().getBytes()[index] & 0xFF;
        index++;
        return val;
    }

    @Override
    public int read(byte b[], int off, int len) throws IOException {
        byte[] bytes = stringBuilder.toString().getBytes();
        for (int i = 0; index < bytes.length; i++) {
            b[i+off] = bytes[index];
            index++;
        }
        return index;
    }

    @Override
    public byte[] readNBytes(int len) throws IOException {
        return stringBuilder.toString().getBytes();
    }

    @Override
    public int available() throws IOException {
        return stringBuilder.toString().getBytes().length - index - 1;
    }

    public void putDataToOutput(String data) throws IOException {
        stringBuilder.append(data);
    }

    public String getContent() {
        return stringBuilder.toString();
    }

}
