package com.chenyanwu.erp.erpframework.controller;

import com.chenyanwu.erp.erpframework.common.Constants;
import com.chenyanwu.erp.erpframework.common.ResultBean;
import com.chenyanwu.erp.erpframework.common.util.StringUtils;
import com.chenyanwu.erp.erpframework.common.util.VerifyCodeUtil;
import com.chenyanwu.erp.erpframework.exception.ExceptionEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/index")
    public ModelAndView index(ModelAndView modelAndView){
        return modelAndView;
    }

    @GetMapping("/index1")
    public ModelAndView index1(ModelAndView modelAndView){
        return modelAndView;
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request){
        logger.info("当前的路径为：" + request.getRequestURI());
        Subject s = SecurityUtils.getSubject();
        logger.info("是否记住登录--》" + s.isRemembered() + "; 是否有权限登录" + s.isAuthenticated());
        if(s.isAuthenticated()){
            return "redirect:index";
        }else {
            return "login";
        }
    }

    @PostMapping("login/main")
    @ResponseBody
    public ResultBean loginMain(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");
        String code = request.getParameter("code");
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return new ResultBean(ExceptionEnum.BUSINESS_ERROR, "用户名或者密码不能为空", "用户名或者密码不能为空", "") ;
        }
        if(StringUtils.isBlank(rememberMe)){
            return new ResultBean(ExceptionEnum.BUSINESS_ERROR, "记住我不能为空", "记住我不能为空", "") ;
        }
        if(StringUtils.isBlank(code)){
            return new ResultBean(ExceptionEnum.BUSINESS_ERROR, "验证码不能为空", "验证码不能为空", "") ;
        }
        Map<String,Object> map = new HashMap<>();
        String error = null;
        HttpSession session = request.getSession();
        if(session == null){
            return new ResultBean(ExceptionEnum.BUSINESS_ERROR, "session超时", "session超时", "") ;
        }
        String trueCode =  (String)session.getAttribute(Constants.VALIDATE_CODE);
        if(StringUtils.isBlank(trueCode)){
            return new ResultBean(ExceptionEnum.BUSINESS_ERROR, "验证码超时", "验证码超时", "") ;
        }
        if(StringUtils.isBlank(code) || !trueCode.toLowerCase().equals(code.toLowerCase())){
            error = "验证码错误";
        }else {
            /*就是代表当前的用户。*/
            Subject user = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password, Boolean.valueOf(rememberMe));
            try {
                user.login(token);
                if (user.isAuthenticated()) {
                    map.put("url","index");
                }
            }catch (IncorrectCredentialsException e) {
                error = "登录密码错误.";
            } catch (ExcessiveAttemptsException e) {
                error = "登录失败次数过多";
            } catch (LockedAccountException e) {
                error = "帐号已被锁定.";
            } catch (DisabledAccountException e) {
                error = "帐号已被禁用.";
            } catch (ExpiredCredentialsException e) {
                error = "帐号已过期.";
            } catch (UnknownAccountException e) {
                error = "帐号不存在";
            } catch (UnauthorizedException e) {
                error = "您没有得到相应的授权！";
            }
        }
        if(StringUtils.isBlank(error)){
            return new ResultBean(map) ;
        }else{
            return new ResultBean(ExceptionEnum.BUSINESS_ERROR, error, error, "") ;
        }
    }

    /**
     * 获取验证码图片和文本(验证码文本会保存在HttpSession中)
     */
    @GetMapping("/genCaptcha")
    public void genCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        String verifyCode = VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_ALL_MIXED, 4, null);
        request.getSession().setAttribute(Constants.VALIDATE_CODE, verifyCode);
        logger.info("本次生成的验证码为[" + verifyCode + "],已存放到HttpSession中");
        response.setContentType("image/jpeg");
        BufferedImage bufferedImage = VerifyCodeUtil.generateImageCode(verifyCode, 116, 36, 5, true, new Color(249, 205, 173), null, null);
        ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());
    }

    @GetMapping("systemLogout")
    public String logOut() {
        SecurityUtils.getSubject().logout();
        return "redirect:/login";
    }
}
