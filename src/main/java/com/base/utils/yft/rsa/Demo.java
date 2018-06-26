package com.base.utils.yft.rsa;

import java.util.Map;
import java.util.Set;

/**
 * Created by zhy on 2017/5/12.
 */
public class Demo {
    @SuppressWarnings("rawtypes")
	public static void main(String[] argus) throws Exception {
        Map<String, String> map = RSAUtils.genKeyPair();
        String publicKey = "";
        String privateKey = "";
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        for (Map.Entry entry : entrySet) {
            System.out.println(entry.getKey() + "-----" + entry.getValue());
            if ("publicKey".equals(entry.getKey())) {
                publicKey = (String) entry.getValue();
            } else if ("privateKey".equals(entry.getKey())) {
                privateKey = (String) entry.getValue();
            }
        }

        System.out.println("--------------公钥加密私钥解密过程-------------------");
        String plainText = "";
        //公钥加密过程
        byte[] cipherData = RSAUtils.encrypt(RSAUtils.loadPublicKeyByStr(publicKey), plainText.getBytes());
        String cipher = Base64.encode(cipherData);
        //私钥解密过程
        byte[] res = RSAUtils.decrypt(RSAUtils.loadPrivateKeyByStr(privateKey), Base64.decode(cipher));
        String restr = new String(res);
        System.out.println("原文：" + plainText);
        System.out.println("加密：" + cipher);
        System.out.println("解密：" + restr);
        System.out.println();

        System.out.println("--------------私钥加密公钥解密过程-------------------");
        plainText = "京N MY908";
        //私钥加密过程
        cipherData = RSAUtils.encrypt(RSAUtils.loadPrivateKeyByStr(privateKey), plainText.getBytes());
        cipher = Base64.encode(cipherData);
        //公钥解密过程
        res = RSAUtils.decrypt(RSAUtils.loadPublicKeyByStr(publicKey), Base64.decode(cipher));
        restr = new String(res);
        System.out.println("原文：" + plainText);
        System.out.println("加密：" + cipher);
        System.out.println("解密：" + restr);
        System.out.println();


    }
}
