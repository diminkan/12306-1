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
        BigInteger bigInt = new BigInteger(1, digest.digest())
        return bigInt.toString(16)
    }
}
