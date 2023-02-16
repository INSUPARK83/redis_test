package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestThreeService {

    private TestOneService testOneService;
    private TestTwoService testTwoService;

    public void test2() {
        testTwoService.test2();
    }

    public void test1() {
        testOneService.test1();
    }
}
