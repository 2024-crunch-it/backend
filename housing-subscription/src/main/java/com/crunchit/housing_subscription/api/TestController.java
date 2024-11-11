package com.crunchit.housing_subscription.api;

import com.crunchit.housing_subscription.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TestController {
    private final TestService testService;

    @GetMapping("/test")
    public ResponseEntity<?> testApi() {

        return new ResponseEntity<>("test ok", HttpStatus.OK);
    }

}