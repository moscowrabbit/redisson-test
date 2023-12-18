package com.example.redissontest.excel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * description:
 * author: 丁波琪 <dingboqi@css.com.cn>
 * date: 2023/12/4 14:48
 * Copyright: ©China software and Technical service Co.Ltd
 */
@Data
public class Test {

    @JsonProperty("CUST_ID")
    private String custId;

    public static void main(String[] args) {

    }
}
