package com.example.demo.controller;

import com.example.demo.dto.LongTest;
import com.example.demo.common.ResponseEntity;
import com.example.demo.common.ResponseWrapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Optional;




@RestController
@RequestMapping("/rest")
public class Controller {

    @GetMapping("/result2")
    public com.example.demo.common.ResponseEntity<LongTest> restTestResult2(){
        return ResponseEntity.success(LongTest.builder().value(12345678901234599L).build());
    }

    @GetMapping("/test")
    public void ttest(){


        // request url
        String url = "http://localhost:9090/rest/result";

// create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();
// create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

// build the request

// make an HTTP GET request with headers

        restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(LongTest.builder().value(1291741231928705024L).build(), headers), new ParameterizedTypeReference<Object>() {
        });

    }

    @PostMapping("/result")
    public ResponseWrapper<LongTest> restTestResult(HttpServletRequest request, @RequestBody LongTest student){

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = (String)headerNames.nextElement();
            String value = request.getHeader(name);
            System.out.println("name = " + name+"   value = " + value);
        }

        String fullUrl = request.getRequestURI() + Optional.ofNullable(request.getQueryString()).map(qs -> "?" + qs).orElse("");

        System.out.println("fullUrl = " + fullUrl);

        return ResponseWrapper.success(student);
    }




}

