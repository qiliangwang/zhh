package com.iceberg.controller;

import com.iceberg.VO.ResultVO;
import com.iceberg.VO.UserVO;
import com.iceberg.constant.CookieConstant;
import com.iceberg.constant.LoginConst;
import com.iceberg.constant.RedisConstant;
import com.iceberg.enums.ResultEnum;
import com.iceberg.form.UserForm;
import com.iceberg.repository.SellerInfoRepository;
import com.iceberg.service.SellerService;
import com.iceberg.config.ProjectUrlConfig;
import com.iceberg.dataobject.SellerInfo;
import com.iceberg.utils.CookieUtil;
import com.iceberg.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 卖家用户
 * @author wangql
 */
@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

//    @GetMapping("/login")
//    public ModelAndView login(@RequestParam("openid") String openid,
//                              HttpServletResponse response,
//                              Map<String, Object> map) {
//
//        //1. openid去和数据库里的数据匹配
//        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
//        if (sellerInfo == null) {
//            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
//            map.put("url", "/sell/seller/order/list");
//            return new ModelAndView("common/error");
//        }
//
//        //2. 设置token至redis
//        String token = UUID.randomUUID().toString();
//        Integer expire = RedisConstant.EXPIRE;
//
//        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openid, expire, TimeUnit.SECONDS);
//
//        //3. 设置token至cookie
//        CookieUtil.set(response, CookieConstant.TOKEN, token, expire);
//
//        return new ModelAndView("redirect:" + projectUrlConfig.getSell() + "/sell/seller/order/list");
//
//    }

    @ResponseBody
    @PostMapping("login")
    public ResultVO findByGroupAndLanguage(@RequestBody UserForm userForm, HttpSession session, HttpServletResponse httpServletResponse) {

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
    public ModelAndView logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Map<String, Object> map) {
        //1. 从cookie里查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null) {
            //2. 清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));

            //3. 清除cookie
            CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
        }

        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }
}
