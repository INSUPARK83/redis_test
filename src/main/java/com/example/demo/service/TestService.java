package com.example.demo.service;

import com.example.demo.dto.TestVo;
import org.springframework.stereotype.Service;

@Service
public class TestService {


    public TestVo getTestSvc(String id){
        TestVo tvo = new TestVo();
        tvo.setId(id);
        tvo.setText( id + "님, 안녕하세요~!");

        System.out.println("[id:" + id + "] Service 에서 연산을 수행합니다");

        return tvo;
    }
}