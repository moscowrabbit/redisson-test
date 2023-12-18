package com.example.redissontest;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * description:
 * author: 丁波琪 <dingboqi@css.com.cn>
 * date: 2023/7/5 16:14
 * Copyright: ©China software and Technical service Co.Ltd
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class RedissonController {

    @Autowired
    private RedissonClient redissonClient;

    @GetMapping("/redisson")
    public String testLock(String key) throws InterruptedException {

        boolean result = redissonClient.getLock(key).tryLock();
        long start = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        TimeCount timeCount = new TimeCount(countDownLatch, key);
        if(!result){
            Thread thread = new Thread(timeCount);
            thread.start();
            countDownLatch.await();
            long end = System.currentTimeMillis();
            log.info("锁释放时间 : " + (end - start)/1000);
        }

        return "success....";
    }

    @GetMapping("/lock")
    public String lock(String key) throws InterruptedException {

        RLock rLock = redissonClient.getLock(key);
        rLock.lock();
        System.out.println("test");
        TimeUnit.MINUTES.sleep(3);
        if(rLock.isLocked() && rLock.isHeldByCurrentThread()){
            rLock.unlock();
        }
        return "success....";
    }

    @GetMapping("/lock1")
    public String lock1(String key) throws InterruptedException {

        RLock rLock = redissonClient.getLock(key);
        rLock.lock();
        System.out.println("test");
        TimeUnit.MINUTES.sleep(3);
        if(rLock.isLocked() && rLock.isHeldByCurrentThread()){
            rLock.unlock();
        }
        return "success....";
    }


    @GetMapping("/bulong/init")
    public String BulongFilterinit(){
        RBloomFilter<String> rBloomFilter = redissonClient.getBloomFilter("sampleBulongFilter");
        rBloomFilter.tryInit(1000000,0.05);
        String result1 = "";
        String result2 = "";
        String result3 = "";
        for(int i = 1; i< 1000000;i++){
            String temp = UUID.randomUUID().toString().replaceAll("-","");
            rBloomFilter.add(temp);
            if(i == 500000){
                result2 = temp;
            }
            if(i == 200000){
                result2 = temp;
            }
            if(i == 990000){
                result2 = temp;
            }
        }
        return result1 + ":" + result2 + ":" + result3;
    }

    @GetMapping("/bulong/get")
    public boolean BulongFilterinit(String key){
        RBloomFilter<String> rBloomFilter = redissonClient.getBloomFilter("sampleBulongFilter");
        return rBloomFilter.contains(key);
    }







    /**
     * TimeCount
     */
    class TimeCount implements Runnable{

        private CountDownLatch countDownLatch;

        private String key;

        /**
         *
         * @param countDownLatch
         */
        public TimeCount(CountDownLatch countDownLatch, String key) {
            this.countDownLatch = countDownLatch;
            this.key = key;
        }

        @Override
        public void run() {

            boolean result = redissonClient.getLock(key).tryLock();
            while(!result){
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                result = redissonClient.getLock(key).tryLock();
            }
            countDownLatch.countDown();
        }
    }
}
