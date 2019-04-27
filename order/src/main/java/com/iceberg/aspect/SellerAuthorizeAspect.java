package com.iceberg.aspect;

import com.iceberg.constant.LoginConst;
import com.iceberg.dataobject.SellerInfo;
import com.iceberg.exception.SellerAuthorizeException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * @author wangql
 */
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private HttpSession session;

    @Pointcut("execution(public * com.iceberg.controller.Seller*.*(..))" +
    "&& !execution(public * com.iceberg.controller.SellerUserController.*(..))")
    public void verify() {}

    @Before("verify()")
    public void doVerify() {
        SellerInfo user = (SellerInfo) session.getAttribute(LoginConst.CURRENT_USER);
        if (user == null) {
            log.warn("【登录校验】session中查不到该用户");
            throw new SellerAuthorizeException();
        }
    }
}
