package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceTimeInfo implements Serializable {
    private Long timeId;
    private Long startAt;
    private Long endAt;
}
