package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceInfo implements Serializable {

    private Map<String,MaintenanceApiInfo> fixedUrlInfoMap;
    private Map<String,MaintenanceApiInfo> asteriskUrlInfoMap;

}
