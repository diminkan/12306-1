package cn.axboy.train.utils

import java.security.MessageDigest

/**
 * @author zcw
 * @version 1.0.0
 * @date 2018/1/22 14:49
 */
class HashCryptUtil {

    static String getMD5(byte[] bytes) {
        MessageDigest digest = MessageDigest.getInstance("MD5")
        digest.update(bytes, 0, bytes.length)
        return byteArray2HexString(digest.digest())
    }

    static String getMD5(String text) {
        if (text == null) {
            return null
        }
        return getMD5(text.getBytes())
    }

    //转化BYTE数组为16进制字符串
    private static String byteArray2HexString(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            String s = Integer.toHexString(b & 0xff);
            if (s.length() == 1) {
                sb.append("0" + s);
            } else {
                sb.append(s);
            }
        }
        return sb.toString();
    }
}
