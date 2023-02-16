package com.example.demo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEntity<T> {
    private T data;
    public static <T> ResponseEntity<T> success(T data) {
        ResponseEntity<T> responseWrapper = new ResponseEntity<>();
        responseWrapper.setData(data);
        return responseWrapper;
    }
}
