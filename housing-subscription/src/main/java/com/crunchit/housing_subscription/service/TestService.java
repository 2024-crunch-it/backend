package com.crunchit.housing_subscription.service;

import com.crunchit.housing_subscription.dto.request.TestRequestDto;
import com.crunchit.housing_subscription.dto.response.TestResponseDto;
import com.crunchit.housing_subscription.entity.Test;
import com.crunchit.housing_subscription.repository.TestRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class TestService {
    private final TestRepository testRepository;

    @Transactional
    public Long save(TestRequestDto requestDto) {
        Test test = requestDto.toEntity();
        return testRepository.save(test).getId();
    }

    @Transactional(readOnly = true)
    public TestResponseDto findById(Long id) {
        Test test = testRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다. id=" + id));
        return new TestResponseDto(test);
    }

    @Transactional(readOnly = true)
    public List<TestResponseDto> findAll() {
        return testRepository.findAll().stream()
                .map(TestResponseDto::new)
                .collect(Collectors.toList());
    }
}
