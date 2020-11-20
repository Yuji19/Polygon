package com.yuji.polygon.controller;

import com.yuji.polygon.config.VerificationCodeProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @className: LoginController
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-18 15:55:00
 */

@RestController
public class LoginController {

    @GetMapping("/verifyCode")
    public void getVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        VerificationCodeProvider provider = new VerificationCodeProvider();
        BufferedImage image = provider.getImage();
        String text = provider.getText();
        //若存在会话则返回该会话，否则新建一个会话
        HttpSession session = request.getSession(true);
        session.setAttribute("verifyCode", text);
        VerificationCodeProvider.output(image, response.getOutputStream());
    }
}
