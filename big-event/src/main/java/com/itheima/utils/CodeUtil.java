package com.itheima.utils;

import java.util.Random;

public class CodeUtil {
    public static String  generateRandomCode() {
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
         return captcha;
    }
}
