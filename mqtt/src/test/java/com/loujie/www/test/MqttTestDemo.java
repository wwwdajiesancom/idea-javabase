package com.loujie.www.test;

import com.loujie.www.server.MqttClientUtils;
import com.loujie.www.utils.StringEncrypt;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @name loujie
 * @date 2019/3/15
 */
public class MqttTestDemo {

    ExecutorService executorService = Executors.newFixedThreadPool(3);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void sha256() {
        String p = "server1";UUID.randomUUID().toString().replace("-", "");
        String result1 = StringEncrypt.string2Sha1(p);
        String result2 = StringEncrypt.string2Sha1(p);
        System.out.println(p);
        System.out.println(result1);
        System.out.println(result2);
    }


    @Test
    public void mst() {
        MqttClientUtils.publish("nxnxnx", "jiaoyubao/open_order/mst1");
        System.out.println("-----end");
    }


}
