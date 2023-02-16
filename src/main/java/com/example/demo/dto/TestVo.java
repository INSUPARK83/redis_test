package com.example.demo.dto;

import lombok.Data;

@Data // Lombok 미적용 시, Getter/Setter 메서드 추가
public class TestVo {

    private String id;
    private String text;

}
