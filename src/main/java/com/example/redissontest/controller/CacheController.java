package com.example.redissontest.controller;

import com.example.redissontest.sax.Book;
import com.example.redissontest.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * description:
 * author: 丁波琪 <dingboqi@css.com.cn>
 * date: 2023/11/3 20:04
 * Copyright: ©China software and Technical service Co.Ltd
 */
@RestController
public class CacheController {

    @Autowired
    private CacheService cacheService;

    @GetMapping("/select-book")
    public Book selectBookDetail(@RequestParam(value = "id") String id){
        return cacheService.selectBookDetail(id);
    }

    @PutMapping("/save-book")
    public Book setBook(@RequestBody Book book){
        return cacheService.saveBook(book);
    }
}
