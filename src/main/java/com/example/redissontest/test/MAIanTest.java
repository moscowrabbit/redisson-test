package com.example.redissontest.test;

import io.netty.util.HashedWheelTimer;

import java.util.concurrent.*;

/**
 * description:
 * author: 丁波琪 <dingboqi@css.com.cn>
 * date: 2023/7/13 8:51
 * Copyright: ©China software and Technical service Co.Ltd
 */
public class MAIanTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        HashedWheelTimer hashedWheelTimer = new HashedWheelTimer();
        hashedWheelTimer.newTimeout((timeout -> {System.out.println("超时了:" + timeout);}),1, TimeUnit.SECONDS);
        hashedWheelTimer.newTimeout((timeout -> {System.out.println("超时了:" + timeout);}),3, TimeUnit.SECONDS);
        hashedWheelTimer.newTimeout((timeout -> {System.out.println("超时了:" + timeout);}),5, TimeUnit.SECONDS);
        TimeUnit.SECONDS.sleep(20);
        hashedWheelTimer.stop();
    }
}
