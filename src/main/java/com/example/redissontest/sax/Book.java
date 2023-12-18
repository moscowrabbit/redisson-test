package com.example.redissontest.sax;

import lombok.Data;

/**
 * description: 书籍
 *
 * author: 丁波琪 <dingboqi@css.com.cn>
 * date: 2023/8/17 9:29
 * Copyright: ©China software and Technical service Co.Ltd
 */
@Data
public class Book {

    /**
     * id
     */
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 作者
     */
    private String author;

    /**
     * 价格
     */
    private String price;

    /**
     * 出版时间
     */
    private String publishDate;
}
