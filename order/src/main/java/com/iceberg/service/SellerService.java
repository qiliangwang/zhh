package com.iceberg.service;

import com.iceberg.dataobject.SellerInfo;

/**
 * 卖家端
 * @author wangql
 */
public interface SellerService {

    /**
     * 通过openid查询卖家端信息
     * @param openid
     * @return
     */
    SellerInfo findSellerInfoByOpenid(String openid);
}
