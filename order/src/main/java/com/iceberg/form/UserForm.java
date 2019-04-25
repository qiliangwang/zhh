package com.iceberg.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author wangql
 */
@Data
public class UserForm {

    @NotEmpty(message = "姓名必填")
    private String username;

    @NotEmpty(message = "姓名必填")
    private String password;
}
