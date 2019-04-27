package com.iceberg.controller;

import com.iceberg.VO.ResultVO;
import com.iceberg.constant.LoginConst;
import com.iceberg.dataobject.SellerInfo;
import com.iceberg.form.UserForm;
import com.iceberg.repository.SellerInfoRepository;
import com.iceberg.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 卖家用户
 * @author wangql
 */
@RestController
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private HttpSession session;

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @GetMapping("mock")
    public ResultVO mockLogin(HttpSession session) {

        SellerInfo user = sellerInfoRepository.findByUsernameAndPassword("wangql", "123456");
        if (user != null) {
            session.setAttribute(LoginConst.CURRENT_USER, user);
            return ResultVOUtil.success(user);
        } else {
            return ResultVOUtil.error(500, "mock失败");
        }
    }

    @PostMapping("login")
    public ResultVO findByGroupAndLanguage(@RequestBody UserForm userForm) {

        SellerInfo user = sellerInfoRepository.findByUsernameAndPassword(userForm.getUsername(), userForm.getPassword());
        if (user != null) {
            user.setPassword("");
            session.setAttribute(LoginConst.CURRENT_USER, user);
            return ResultVOUtil.success(user);
        } else {
            return ResultVOUtil.error(500, "用户不存在");
        }
    }

    @GetMapping("/logout")
    public void logout() {
        session.removeAttribute(LoginConst.CURRENT_USER);
    }
}
