package com.crunchit.housing_subscription.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/redis")
@RequiredArgsConstructor
public class RedisController {

    private final RedisTemplate<String, String> redisTemplate;

    @PostMapping("/set")
    public String setValue(@RequestParam String key, @RequestParam String value) {
        redisTemplate.opsForValue().set(key, value);
        return "Saved key: " + key + ", value: " + value;
    }

    @GetMapping("/get")
    public String getValue(@RequestParam String key) {
        String value = redisTemplate.opsForValue().get(key);
        return value != null ? "Retrieved value: " + value : "Key not found";
    }

    @DeleteMapping("/delete")
    public String deleteKey(@RequestParam String key) {
        Boolean deleted = redisTemplate.delete(key);
        return deleted != null && deleted ? "Deleted key: " + key : "Key not found or already deleted";
    }
}