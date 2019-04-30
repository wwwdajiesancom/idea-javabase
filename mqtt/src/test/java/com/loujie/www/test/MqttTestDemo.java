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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @name loujie
 * @date 2019/3/15
 */
public class MqttTestDemo {

    ExecutorService executorService = Executors.newFixedThreadPool(3);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // @Test
    public void sha256() {
        String result1 = StringEncrypt.String2SHA256("zhangfei");
        String result2 = StringEncrypt.String2SHA256StrJava("server");
        System.out.println(result1);
        System.out.println(result2);
    }

    @Test
    public void mst() {
        MqttClientUtils.publish("nxnxnx", "jiaoyubao/open_order/mst1");
        for (int i = 0; i < 11; i++) {
            int fi = i;
            executorService.execute(() -> {
                String date = sdf.format(Calendar.getInstance().getTime());
                System.out.println(date + ":" + fi);
                MqttClientUtils.publish(date + ":" + fi, "jiaoyubao/open_order/mst1");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }
        System.out.println("-----end");
    }


}
