package com.example.redissontest.excel;

import lombok.Data;

/**
 * description:
 * author: 丁波琪 <dingboqi@css.com.cn>
 * date: 2023/12/4 9:53
 * Copyright: ©China software and Technical service Co.Ltd
 */
@Data
public class UserInfo {

    private String userId;

    private String orgId;

    private String parentOrgId;

    private String userName;

    private String mobile;

    private String cardId;

    private String simpleOrgName;

    private String fullOrgName;

    private String orgName;
}
