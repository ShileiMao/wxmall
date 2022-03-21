package com.example.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpConnection;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.ssl.DefaultTrustManager;
import cn.hutool.json.JSONObject;
import com.example.common.Result;
import com.example.common.ResultCode;
import com.example.common.config.AppConfigs;
import com.example.common.config.LoginMethod;
import com.example.entity.UserInfo;
import com.example.entity.Account;
import com.example.entity.UserAddr;
import com.example.exception.CustomException;
import com.example.service.UserInfoService;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertStoreException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RestController
public class AccountController {
    @Autowired
    private AppConfigs appConfigs;

    @Resource
    private UserInfoService userInfoService;

    @GetMapping("/logout")
    public Result logout(HttpServletRequest request) {
        request.getSession().setAttribute("user", null);
        return Result.success();
    }

    @GetMapping("/auth")
    public Result getAuth(HttpServletRequest request) {
        Object user = request.getSession().getAttribute("user");
        if (user == null) {
            return Result.error("401", "未登录");
        }
        return Result.success((UserInfo) user);
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public Result<UserInfo> register(@RequestBody UserInfo userInfo, HttpServletRequest request) {
        if (StrUtil.isBlank(userInfo.getName()) || StrUtil.isBlank(userInfo.getPassword())) {
            throw new CustomException(ResultCode.PARAM_ERROR);
        }
        UserInfo register = userInfoService.add(userInfo);
        HttpSession session = request.getSession();
        session.setAttribute("user", register);
        session.setMaxInactiveInterval(120 * 60);
        return Result.success(register);
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result<UserInfo> login(@RequestBody UserInfo userInfo, HttpServletRequest request) {
        if (StrUtil.isBlank(userInfo.getName()) || StrUtil.isBlank(userInfo.getPassword())) {
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);
        }
        UserInfo login = userInfoService.login(userInfo.getName(), userInfo.getPassword(), LoginMethod.NAME_PWD.value);
        HttpSession session = request.getSession();
        session.setAttribute("user", login);
        session.setMaxInactiveInterval(120 * 60);
        return Result.success(login);
    }

    @GetMapping("/wechatLogin")
    public UserInfo wechatLogin(@RequestParam String code, @RequestParam String nickName, @RequestParam int gender, HttpServletRequest request) {
        if (StrUtil.isBlank(code)) {
            throw new CustomException(ResultCode.LOGIN_CODE_EMPTY);
        }
        String appID = appConfigs.getWechatAppID();
        String appSec = appConfigs.getWecahtAppSec();

        String url = String.format("https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code", appID, appSec, code);
    // https://api.weixin.qq.com/sns/jscode2session?appid=wx64b059bb85cc3e29&secret=2cb420e6c252b6b7fbe91b1b61d8b412&js_code=021yy100042ovN1Wji000YjuxS0yy10l&grant_type=authorization_code
        HttpRequest httpRequest = HttpRequest.get(url);

        HttpResponse response = httpRequest.execute();
        String respBody = response.body();

        JSONObject jsonObject = new JSONObject(respBody);
        String sessionKey = jsonObject.getStr("session_key");
        int expires = jsonObject.getInt("expires_in");
        String openid = jsonObject.getStr("openid");

        if(sessionKey != null &&  openid != null) {
            try {
                UserInfo userInfo = userInfoService.searchExistingUser(openid, LoginMethod.WECHAT.value);
                userInfo.setPassword(sessionKey);
                userInfoService.update(userInfo);
                return userInfo;
            } catch (Exception e) {
                System.out.println("Wechat user not exists, creating one");
//                e.printStackTrace();
            }

            UserInfo userInfo = new UserInfo();
            userInfo.setName(openid);
            userInfo.setPassword(sessionKey);
            userInfo.setLoginMethod(LoginMethod.WECHAT.value);
            userInfo.setNickname(nickName);
            userInfo.setAccount(0.0);

            String translatedGender = "未知";
            switch (gender) {
                case 1:
                    translatedGender = "男";
                    break;
                case 2:
                    translatedGender = "女";
                    break;
                default:
                    translatedGender = "未知";
                    break;
            }
            userInfo.setSex(translatedGender);

            userInfo = userInfoService.add(userInfo);
            return userInfo;
        }
        // 创建账户失败
        throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);
    }

    /**
     * 重置密码为123456
     */
    @PutMapping("/resetPassword")
    public Result<UserInfo> resetPassword(@RequestParam String username) {
        return Result.success(userInfoService.resetPassword(username));
    }

    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody UserInfo info, HttpServletRequest request) {
        UserInfo account = (UserInfo) request.getSession().getAttribute("user");
        if (account == null) {
            return Result.error(ResultCode.USER_NOT_EXIST_ERROR.code, ResultCode.USER_NOT_EXIST_ERROR.msg);
        }
        String oldPassword = SecureUtil.md5(info.getPassword());
        if (!oldPassword.equals(account.getPassword())) {
            return Result.error(ResultCode.PARAM_PASSWORD_ERROR.code, ResultCode.PARAM_PASSWORD_ERROR.msg);
        }
        account.setPassword(SecureUtil.md5(info.getNewPassword()));
        userInfoService.update(account);

        // 清空session，让用户重新登录
        request.getSession().setAttribute("user", null);
        return Result.success();
    }

    @GetMapping("/mini/userInfo/{id}/{level}")
    public Result<Account> miniLogin(@PathVariable Long id, @PathVariable Integer level) {
        Account account = userInfoService.findByIdAndLevel(id, level);
        return Result.success(account);
    }

    /**
     * 修改密码
     */
    @PutMapping("/changePassword")
    public Result<Boolean> changePassword(@RequestParam Long id,
            @RequestParam String newPassword) {
        return Result.success(userInfoService.changePassword(id, newPassword));
    }

    @GetMapping("/getSession")
    public Result<Map<String, String>> getSession(HttpServletRequest request) {
        UserInfo account = (UserInfo) request.getSession().getAttribute("user");
        if (account == null) {
            return Result.success(new HashMap<>(1));
        }
        Map<String, String> map = new HashMap<>(1);
        map.put("username", account.getName());
        return Result.success(map);
    }

    @GetMapping("/postAddress/{id}")
    public Result<UserAddr> getAddress(@PathVariable Long id) {
        UserAddr addr = userInfoService.getPostAddress(id);

        return Result.success(addr);
    }

    @PostMapping("/updatePostAddress")
    public Result<UserAddr> updatePostAddr(@RequestBody UserAddr info) {

        UserAddr addr = userInfoService.updateOrInsertAddress(info);

        return Result.success(addr);
    }
}
