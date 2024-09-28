package com.qa.utils;

import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

    public static void saveTokenToFile(String token) throws IOException {
        try (FileWriter fileWriter = new FileWriter("token.txt")) {
            fileWriter.write(token);
        }
    }
}
