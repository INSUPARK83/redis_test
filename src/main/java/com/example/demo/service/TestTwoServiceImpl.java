package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestTwoServiceImpl implements TestTwoService{
    //private final TestOneService testOneService;
    private final TestThreeService testThreeService;


    public void test2(){
        testThreeService.test1();
    }
}
