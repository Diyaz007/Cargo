package com.finalproject.finalproject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class ImageToJsonExample {
    public static void main(String[] args) {
        try {
            // Чтение изображения в байтовый массив
            File file = new File("path/to/your/image.jpg");
            byte[] imageBytes = Files.readAllBytes(file.toPath());

            // Конвертация в строку Base64
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            // Создание JSON-объекта
            JSONObject json = new JSONObject();
            json.put("image", base64Image);

            // Печать JSON в консоль
            System.out.println(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
