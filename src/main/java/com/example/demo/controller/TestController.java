package com.example.demo.controller;

import com.example.demo.config.RedisManager;
import com.example.demo.dto.MaintenanceApiInfo;
import com.example.demo.dto.MaintenanceInfo;
import com.example.demo.dto.MaintenanceTimeInfo;
import com.example.demo.dto.TestVo;
import com.example.demo.enums.MethodType;
import com.example.demo.service.TestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class TestController {



   private final TestService svc;
   //private final RedisTemplate<String,Object> redisTemplate;

   private final RedisManager redisManager;

    @Cacheable(value = "TestVo", key = "#id", cacheManager = "cacheManager", unless = "#id == ''", condition = "#id.length > 2")
    @GetMapping("/redis-test")
    public TestVo getData(@RequestParam String id ){
        return svc.getTestSvc(id);
    }


    @GetMapping("/redis-test2")
    public String getData2() throws Exception {


        MaintenanceApiInfo maintenanceApiInfo1 = MaintenanceApiInfo.builder().apiId(1L).path("/profile/c").methodType(MethodType.POST).maintenanceTimeInfos(
                List.of(MaintenanceTimeInfo.builder().timeId(new Long(new Random().nextInt(10))).startAt(new Random().nextLong()).endAt(new Random().nextLong()).build()
                        , MaintenanceTimeInfo.builder().timeId(new Long(new Random().nextInt(10))).startAt(new Random().nextLong()).endAt(new Random().nextLong()).build()
                        , MaintenanceTimeInfo.builder().timeId(new Long(new Random().nextInt(10))).startAt(new Random().nextLong()).endAt(new Random().nextLong()).build())).build();

        MaintenanceApiInfo maintenanceApiInfo2 = MaintenanceApiInfo.builder().apiId(1L).path("/profile/*").methodType(MethodType.POST).maintenanceTimeInfos(
                List.of(MaintenanceTimeInfo.builder().timeId(new Long(new Random().nextInt(10))).startAt(new Random().nextLong()).endAt(new Random().nextLong()).build()
                        , MaintenanceTimeInfo.builder().timeId(new Long(new Random().nextInt(10))).startAt(new Random().nextLong()).endAt(new Random().nextLong()).build()
                        , MaintenanceTimeInfo.builder().timeId(new Long(new Random().nextInt(10))).startAt(new Random().nextLong()).endAt(new Random().nextLong()).build())).build();


        MaintenanceApiInfo maintenanceApiInfo3 = MaintenanceApiInfo.builder().apiId(1L).path("/game/a").methodType(MethodType.POST).maintenanceTimeInfos(
                List.of(MaintenanceTimeInfo.builder().timeId(new Long(new Random().nextInt(10))).startAt(new Random().nextLong()).endAt(new Random().nextLong()).build()
                        , MaintenanceTimeInfo.builder().timeId(new Long(new Random().nextInt(10))).startAt(new Random().nextLong()).endAt(new Random().nextLong()).build()
                        , MaintenanceTimeInfo.builder().timeId(new Long(new Random().nextInt(10))).startAt(new Random().nextLong()).endAt(new Random().nextLong()).build())).build();


        MaintenanceApiInfo maintenanceApiInfo4 = MaintenanceApiInfo.builder().apiId(1L).path("/game/*").methodType(MethodType.POST).maintenanceTimeInfos(
                List.of(MaintenanceTimeInfo.builder().timeId(new Long(new Random().nextInt(10))).startAt(new Random().nextLong()).endAt(new Random().nextLong()).build()
                        , MaintenanceTimeInfo.builder().timeId(new Long(new Random().nextInt(10))).startAt(new Random().nextLong()).endAt(new Random().nextLong()).build()
                        , MaintenanceTimeInfo.builder().timeId(new Long(new Random().nextInt(10))).startAt(new Random().nextLong()).endAt(new Random().nextLong()).build())).build();

        Map<String, MaintenanceApiInfo> fixedUrlInfoMap = List.of(maintenanceApiInfo1, maintenanceApiInfo3).stream().collect(Collectors.toMap(MaintenanceApiInfo::getPath, Function.identity()));
        Map<String, MaintenanceApiInfo> asteriskUrlInfoMap = List.of(maintenanceApiInfo2, maintenanceApiInfo4).stream().collect(Collectors.toMap(MaintenanceApiInfo::getPath, Function.identity()));

        MaintenanceInfo maintenanceInfo = MaintenanceInfo.builder().fixedUrlInfoMap(fixedUrlInfoMap).asteriskUrlInfoMap(asteriskUrlInfoMap).build();
        redisManager.saveData("Maintenance",maintenanceInfo, 60L);
//        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
//        opsForValue.set("Maintenance",maintenanceInfo);
//        Object maintenance = opsForValue.get("Maintenance");
//        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
//
//        hashOperations.putAll("Maintenance1",fixedUrlInfoMap);
//        hashOperations.putAll("Maintenance2",asteriskUrlInfoMap);
//
//        Map maintenance2 = (HashMap) hashOperations.get("Maintenance2", "/game/*");
//        ObjectMapper objectMapper = new ObjectMapper();

        //MaintenanceApiInfo obj = objectMapper.readValue(hashOperations.get("Maintenance2", "/game/*"), new TypeReference<>() {});
        // MaintenanceInfo maintenance1 = this.getData("Maintenance", MaintenanceInfo.class);


        MaintenanceInfo maintenance1 = redisManager.getData("Maintenance", MaintenanceInfo.class);
       // String maintenance = (String) redisTemplate.opsForValue().get("Maintenance");
       // ObjectMapper objectMapper = new ObjectMapper();
       // String content = new JSONObject(jsonResult).toString();
       // MaintenanceInfo obj = objectMapper.readValue(maintenance, MaintenanceInfo.class);
        //MaintenanceInfo maintenance1 = this.getData("Maintenance", MaintenanceInfo.class);

        System.out.println("obj = " + maintenance1);

        return "ggg";
    }

//    public <T> T getData(String key, Class<T> classType) throws Exception {
//        String jsonResult = (String) redisTemplate.opsForValue().get(key);
//        if (StringUtils.isBlank(jsonResult)) {
//            return null;
//        }
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        T obj = objectMapper.readValue(jsonResult, classType);
//        return obj;
//    }
}

