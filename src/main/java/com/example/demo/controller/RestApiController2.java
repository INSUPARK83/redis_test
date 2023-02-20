package com.example.demo.controller;

import com.example.demo.dto.TestVo;
import com.example.demo.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestApiController2 {


    private final TestService svc;


    @RequestMapping("/")
    public TestVo getData(){
        return svc.getTestSvc("33");
    }

    @RequestMapping("/v1/")
    public TestVo getData2(){
        return svc.getTestSvc("v1");
    }

    @RequestMapping("/v1/{id}")
    public TestVo getData2(@PathVariable String id){
        return svc.getTestSvc("v1");
    }
}
