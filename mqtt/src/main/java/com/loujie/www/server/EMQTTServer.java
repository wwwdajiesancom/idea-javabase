package com.loujie.www.server;

import com.alibaba.druid.support.json.JSONUtils;
import jdk.nashorn.internal.parser.JSONParser;
import jdk.nashorn.internal.runtime.JSONFunctions;
import org.fusesource.mqtt.client.*;

import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @name loujie
 * @date 2019/3/12
 */
public class EMQTTServer {

    private static final String host = "118.144.248.25";//"lm.loujie.com";
    private static final int port = 1883;

    private final static boolean CLEAN_START = true;
    private final static String CLIENT_ID = "server";
    private final static short KEEP_ALIVE = 30;// 低耗网络，但是又需要及时获取数据，心跳30s

    public static Topic[] topics2 = {
            new Topic("/talk/login", QoS.EXACTLY_ONCE), // 2  只有一次
            new Topic("/talk/login", QoS.AT_LEAST_ONCE),  // 1 至少一次
            new Topic("/talk/login", QoS.AT_MOST_ONCE)};  // 0 至多一次

    public final static long RECONNECTION_ATTEMPT_MAX = 6;
    public final static long RECONNECTION_DELAY = 2000;

    public final static int SEND_BUFFER_SIZE = 64;// 发送最大缓冲

    public static void main(String[] args) throws InterruptedException {
        MQTT mqtt = new MQTT();
        try {
            //==MQTT设置说明
            //设置服务端的ip
            mqtt.setHost(host, port);
            //连接前清空会话信息 ,若设为false，MQTT服务器将持久化客户端会话的主体订阅和ACK位置，默认为true
            mqtt.setCleanSession(CLEAN_START);
            //设置心跳时间 ,定义客户端传来消息的最大时间间隔秒数，服务器可以据此判断与客户端的连接是否已经断开，从而避免TCP/IP超时的长时间等待
            mqtt.setKeepAlive(KEEP_ALIVE);
            //设置客户端id,用于设置客户端会话的ID。在setCleanSession(false);被调用时，MQTT服务器利用该ID获得相应的会话。
            //此ID应少于23个字符，默认根据本机地址、端口和时间自动生成
            mqtt.setClientId(CLIENT_ID);

            mqtt.setUserName("server");
            mqtt.setPassword("server1");

            //==失败重连接设置说明
            //设置重新连接的次数 ,客户端已经连接到服务器，但因某种原因连接断开时的最大重试次数，超出该次数客户端将返回错误。-1意为无重试上限，默认为-1
            mqtt.setReconnectAttemptsMax(RECONNECTION_ATTEMPT_MAX);
            //设置重连的间隔时间  ,首次重连接间隔毫秒数，默认为10ms
            mqtt.setReconnectDelay(RECONNECTION_DELAY);
            //设置socket发送缓冲区大小，默认为65536（64k）
            mqtt.setSendBufferSize(SEND_BUFFER_SIZE);
            ////设置发送数据包头的流量类型或服务类型字段，默认为8，意为吞吐量最大化传输
            mqtt.setTrafficClass(8);

            //==带宽限制设置说明
            mqtt.setMaxReadRate(0);//设置连接的最大接收速率，单位为bytes/s。默认为0，即无限制
            mqtt.setMaxWriteRate(0);//设置连接的最大发送速率，单位为bytes/s。默认为0，即无限制

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        //使用Future创建连接
        final FutureConnection connection = mqtt.futureConnection();
        connection.connect();
        int count = 1;
        count++;
        // 用于发布消息，目前手机段不需要向服务端发送消息
        //主题的内容
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 1);
        map.put("orderNo", "201904241411047LgE1");
        map.put("date", Calendar.getInstance().getTimeInMillis());
        String message = JSONUtils.toJSONString(map);
        String topic = "jiaoyubao/open_order/app1234";
        Future<Void> future = connection.publish(topic, message.getBytes(), QoS.AT_LEAST_ONCE,
                false);
        try {
            future.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("MQTTFutureServer.publish Message " + "Topic Title :" + topic + " context :" + message);
    }

}
