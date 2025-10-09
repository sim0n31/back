package com.upc.molinachirinostp.controller; // usa el mismo package de tus otros controllers

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PingController {

    @GetMapping("/ping")
    public Map<String, String> ping() {
        return Map.of(
                "status", "ok",
                "time", OffsetDateTime.now().toString()
        );
    }
}
