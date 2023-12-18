package com.example.redissontest.test;

/**
 * description:
 * author: 丁波琪 <dingboqi@css.com.cn>
 * date: 2023/7/17 10:19
 * Copyright: ©China software and Technical service Co.Ltd
 */
public class ChildInfo extends ParentInfo{

    private String name;

    public void printName(){
        System.out.println("this name = " + name);
    }
}
