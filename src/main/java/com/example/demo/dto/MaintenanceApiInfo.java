package com.example.demo.dto;

import com.example.demo.enums.MethodType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceApiInfo implements Serializable {
    private Long apiId;
    private String path;
    private MethodType methodType;
    private List<MaintenanceTimeInfo> maintenanceTimeInfos;

}
