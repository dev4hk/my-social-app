package org.example.mysocialapp.util;

import org.apache.tomcat.util.codec.binary.Base64;
import org.example.mysocialapp.entity.Post;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class FileUtils {

    public static byte[] compressImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4 * 1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }

        try {
            outputStream.close();
        } catch (Exception ignored) {

        }
        return outputStream.toByteArray();
    }

    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4 * 1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception ignored) {

        }
        return outputStream.toByteArray();
    }

    public static String loadFileString(String filePath) throws IOException {
        if (filePath == null) {
            return filePath;
        }
        Resource resource = null;
        if(!filePath.isEmpty()) {
            resource = getResource(filePath);
        }
        byte[] fileBytes = Objects.requireNonNull(resource).getContentAsByteArray();
        return Base64.encodeBase64String(fileBytes);
    }

    private static Resource getResource(String filePath) throws FileNotFoundException, MalformedURLException {
        if(!Files.exists(Path.of(filePath))) {
            throw new FileNotFoundException(filePath + " was not found.");
        }
        return new UrlResource(Path.of(filePath).toUri());
    }

}
