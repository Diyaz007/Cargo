package com.finalproject.finalproject.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class CurrencyService {

    private final RestTemplate restTemplate;

    public CurrencyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getRandomQuote() {
        String apiUrl = "https://api.forismatic.com/api/1.0/?method=getQuote&lang=en&format=json";

        try {
            // Выполняем запрос к API
            Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);
            if (response != null && response.containsKey("quoteText")) {
                return (String) response.get("quoteText");
            }
            return "Не удалось получить цитату.";
        } catch (Exception e) {
            return "Ошибка при запросе к API: " + e.getMessage();
        }
    }
}
