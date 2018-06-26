package com.base.utils.yft.rsa;

import com.base.utils.yft.util.StringUtil;


/**
 * Created by zhy on 2017/6/19.
 */
public class AESDemo {
    public static void main(String[] argus) throws Exception {
//        String str = "PClWxTlOWKosecpM+vYfmQ==";
        String str = "70233C94000A9DD65BF160E19D2FF24D";
        String aesKey = "12345678901234512345678901603015";
        System.out.println("AES解密结果为:" + AESCrypt.encrypt(str, aesKey, AESCrypt.iv));


        System.out.println("============================================");
        String aes = StringUtil.getRandomString(16);
        String amt = "10000";
        String result = AESCrypt.encrypt(amt, aes, AESCrypt.iv);

        System.out.print("aes: " + aes + ", txnAmt: " + amt + ",结果为：" + result);

    }
}
