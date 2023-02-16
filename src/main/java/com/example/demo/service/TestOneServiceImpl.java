package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestOneServiceImpl implements TestOneService{

    private final TestThreeService testThreeService;
   // private final TestTwoService testTwoService;

    public void test1() {
        testThreeService.test2();;
    }
}
