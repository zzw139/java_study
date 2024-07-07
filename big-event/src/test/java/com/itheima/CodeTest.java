package com.itheima;

import org.junit.jupiter.api.Test;

import java.util.Random;

public class CodeTest {

    // 生成指定长度的随机数字验证码
    @Test
    private void generateRandomCode() {
        String captcha = "";
        int length = 4;
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // 随机选择是使用数字还是大写字母
            int choice = random.nextInt(2);
            if (choice == 0) {
                // 生成数字
                captcha += String.valueOf(random.nextInt(10));
            } else {
                // 生成大写字母
                captcha += (char) (random.nextInt(26) + 'A');
            }
        }
        System.out.println("验证码：" + captcha);
    }
}
