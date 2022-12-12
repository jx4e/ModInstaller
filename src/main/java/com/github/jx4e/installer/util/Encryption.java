package com.github.jx4e.installer.util;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author Jake (github.com/jx4e)
 * @since 12/12/2022
 **/

public class Encryption {
    private final String keyString;
    private final byte[] keyBytes;
    private final byte[] bytes;

    public Encryption(String key, byte[] bytes) {
        this.keyString = key;
        this.bytes = bytes;
        this.keyBytes = generateKeyFromKeyString();
    }

    public static void main(String[] args) throws MalformedURLException {
        try (FileInputStream in = new FileInputStream(new File("C:/Users/Jake/Downloads/File"));
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer, 0, 1024)) != -1) {
                out.write(buffer, 0, read);
            }
            Encryption e = new Encryption("Key", out.toByteArray());
            Encryption e1 = new Encryption("Key", e.encrypt());

            try (ByteArrayInputStream in1 = new ByteArrayInputStream(e1.decrypt());
                 FileOutputStream out1 = new FileOutputStream("C:/Users/Jake/Downloads/File1")) {
                byte[] b1 = new byte[1024];
                int r1;
                while ((r1 = in1.read(b1, 0, 1024)) != -1) {
                    out1.write(b1, 0, r1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] generateKeyFromKeyString() {
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);

        int charIterator = 0;

        while (buffer.hasRemaining()) {
            buffer.put((byte) keyString.charAt(charIterator));

            charIterator++;

            if (charIterator >= keyString.length()) {
                charIterator = 0;
            }
        }

        return buffer.array();
    }

    public byte[] encrypt() {
        if (keyBytes.length != bytes.length) throw new RuntimeException("Key doesnt match length of bytes");

        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);

        for (int i = 0; i < bytes.length; i++) {
            byte b = (byte) (bytes[i] ^ keyBytes[i]);
            buffer.put(b);
        }

        return buffer.array();
    }

    public byte[] decrypt() {
        if (keyBytes.length != bytes.length) throw new RuntimeException("Key doesnt match length of bytes");

        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);

        for (int i = 0; i < bytes.length; i++) {
            byte b = (byte) (keyBytes[i] ^ bytes[i]);
            buffer.put(b);
        }

        buffer.flip();

        return buffer.array();
    }
}
