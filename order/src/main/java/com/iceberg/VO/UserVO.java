package com.iceberg.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 商品(包含类目)
 * @author wangql
 */
@Data
public class UserVO {

    @JsonProperty("userId")
    private String id;

    private String name;

    private String token;

    private String role;
}
