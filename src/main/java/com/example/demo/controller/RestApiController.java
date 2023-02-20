package com.example.demo.controller;


import com.example.demo.dto.TestVo;
import com.example.demo.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestApiController {

    private final TestService svc;


    @RequestMapping
    public TestVo getData(){
        return svc.getTestSvc("12");
    }
}
