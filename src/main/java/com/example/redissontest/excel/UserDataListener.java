package com.example.redissontest.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.ReadListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * description:
 * author: 丁波琪 <dingboqi@css.com.cn>
 * date: 2023/12/4 10:16
 * Copyright: ©China software and Technical service Co.Ltd
 */
@Slf4j
public class UserDataListener{

    public static void read(String fileString){
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        // 这里默认每次会读取100条数据 然后返回过来 直接调用使用数据就行
        // 具体需要返回多少行可以在`PageReadListener`的构造函数设置
        EasyExcel.read(fileString, UserInfo.class, new PageReadListener<UserInfo>(dataList -> {
                saveToES(dataList);
        })).sheet().doRead();
    }

    private static void saveToES(List<UserInfo> userInfos){
        RestTemplate restTemplate = new RestTemplate();
        StringBuffer stringBuffer = new StringBuffer();
        for(UserInfo userInfo : userInfos){
            stringBuffer.append("{\"create\": {\"_index\": \"hunan_emp_user\", \"_id\": \""
                    + UUID.randomUUID().toString().replaceAll("-","")+"\"}}").append("\n");
            stringBuffer.append(JSON.toJSONString(userInfo)).append("\n");
        }
        System.out.println(stringBuffer);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(stringBuffer.toString(), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://192.168.126.11:9200/_bulk",httpEntity, String.class);
        System.out.println(JSON.toJSONString(response));
    }

    public static void main(String[] args) {
        //UserDataListener.read("D:/USER_INFO.xlsx");

        String jsonStr = "{\"CUST_ID\":\"张三\"}";
        Test test = JSONObject.parseObject(jsonStr,Test.class);
        System.out.println(test.getCustId());
    }
}
