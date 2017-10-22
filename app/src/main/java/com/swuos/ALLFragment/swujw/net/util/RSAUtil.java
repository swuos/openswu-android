package com.swuos.ALLFragment.swujw.net.util;

import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by M on 2017/6/13.
 */

public class RSAUtil {
    private static final int MAX_ENCRYPT_BLOCK = 128;
    public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSFIqSxUFPH92i7UrRPyJbHOJPNlVSsIvNRSkvayhfXNRBSEEzqm+huoXZOWkyynNalobX2pdKkJsjvaO2S4xZF8lHGCmjcHxosu0dDBoggE4rodSO0l2JyVXE0woX6NH6cn1LeHS+KDSQa3xZ/6/+Q/acGRCFeYdTeUJUa+cJUQIDAQAB";
    private static final String KEY_ALGORITHM = "RSA";

    /**
     * 公钥加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    private static String encryptByPublicKey(String data) {
        byte[] dataByte = data.getBytes();
        byte[] keyBytes = Base64.decode(PUBLIC_KEY, Base64.DEFAULT);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = null;
        byte[] encryptedData = null;
        try {
            keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key publicK = keyFactory.generatePublic(x509KeySpec);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            // 对数据加密
            // Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicK);
            int inputLen = dataByte.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(dataByte, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(dataByte, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            encryptedData = out.toByteArray();
            out.close();
        } catch (NoSuchAlgorithmException | IOException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | InvalidKeySpecException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(encryptedData, Base64.DEFAULT);
    }

    public static String encrypt(String data) {
        int length = data.length();
        StringBuilder builder = new StringBuilder();
        try {
            for (int i = 0; i <= length / 100; i++) {
                if (length - i * 100 > 100) {
                    builder.append(encryptByPublicKey(data.substring(i * 100, (i + 1) * 100)));
                } else {
                    builder.append(encryptByPublicKey(data.substring(i * 100, length)));
                }
                builder.append(",");

            }
            System.out.println(builder.substring(0, builder.length() - 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
