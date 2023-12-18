package com.example.redissontest.service;

import com.example.redissontest.sax.Book;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * description: 缓存业务类
 * author: 丁波琪 <dingboqi@css.com.cn>
 * date: 2023/11/3 20:08
 * Copyright: ©China software and Technical service Co.Ltd
 */
@Service("cachesService")
public class CacheService {


    @Cacheable(value = "test", key = "'p_' + #id")
    public Book selectBookDetail(String id){

        Book book = new Book();
        book.setId(id);
        book.setAuthor("莫言");
        book.setPrice("100");
        book.setTitle("丰乳肥臀");
        book.setPublishDate(new Date().toLocaleString());
        System.out.println("未命中缓存...");
        return book;
    }

    @CachePut(value = "test", key = "'p_' + #id")
    public Book saveBook(Book book){
        return book;
    }
}
