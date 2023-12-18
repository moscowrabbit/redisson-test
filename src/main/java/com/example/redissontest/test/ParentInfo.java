package com.example.redissontest.test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * description:
 * author: 丁波琪 <dingboqi@css.com.cn>
 * date: 2023/7/17 10:19
 * Copyright: ©China software and Technical service Co.Ltd
 */
public class ParentInfo {

    protected String name;

    public void setName(String name){
        this.name = name;
    }

    public void printNameP(){
        System.out.println("parent name = " + name);
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(()->{

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 15;
        }, Executors.newFixedThreadPool(10));

        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(()->{

            return 10;
        }, Executors.newFixedThreadPool(10));

        CompletableFuture<Void> test = completableFuture1.allOf(completableFuture1,completableFuture2).thenRun(()->{
            try {
                System.out.println("结算后的结果 : " + completableFuture1.get() + completableFuture2.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        test.get();
        System.out.println("看是不是一部的！");

    }
}
