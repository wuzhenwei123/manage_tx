package com.base.utils.yft.util;

import java.util.Random;

/**
 * Created by zhy on 2017/5/15.
 */
public class StringUtil {
    /**
     * 闅忔満鐢熸垚瀛楃涓�
     * @param length
     * @return
     */
    public static String getRandomString(int length) { //length琛ㄧず鐢熸垚瀛楃涓茬殑闀垮害
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
