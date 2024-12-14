package com.finalproject.finalproject.controllers;

import com.finalproject.finalproject.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RestController
public class IntegApiController {
    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/getQuote")
    public ResponseEntity<String> getCurrency() {
        try {
            String quote = currencyService.getRandomQuote();
            return ResponseEntity.ok(quote);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
