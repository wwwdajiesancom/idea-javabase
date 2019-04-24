package com.loujie.www.test;

import com.loujie.www.utils.StringEncrypt;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @name loujie
 * @date 2019/3/15
 */
public class MqttTestDemo {


    @Test
    public void sha256() {
        String result1 = StringEncrypt.String2SHA256("zhangfei");
        String result2 = StringEncrypt.String2SHA256StrJava("server");
        System.out.println(result1);
        System.out.println(result2);
    }


}
