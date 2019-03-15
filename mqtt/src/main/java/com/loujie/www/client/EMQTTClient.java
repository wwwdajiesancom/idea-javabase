package com.loujie.www.client;


import org.fusesource.mqtt.client.Future;
import org.fusesource.mqtt.client.FutureConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

import java.net.URISyntaxException;

/**
 * @name loujie
 * @date 2019/3/12
 */
public class EMQTTClient {

    private static final String host = "lm.loujie.com";
    private static final int port = 1883;
    public final static long RECONNECTION_ATTEMPT_MAX = 6;
    public final static long RECONNECTION_DELAY = 2000;
    private final static short KEEP_ALIVE = 30;// 低耗网络，但是又需要及时获取数据，心跳30s
    public final static int SEND_BUFFER_SIZE = 64;// 发送最大缓冲为2M

    public static Topic[] topics = {
//            new Topic("/talk/login", QoS.EXACTLY_ONCE), //  2 只有一次
//            new Topic("/talk/login", QoS.AT_LEAST_ONCE),  // 1 至少一次
            new Topic("/talk/bbbb", QoS.AT_MOST_ONCE)};  // 0 至多一次

    private final static String CLIENT_ID = "client";

    public static void main(String[] args) {

        MQTT mqtt = new MQTT();
        try {
            // 设置mqtt broker的ip和端口
            mqtt.setHost(host, port);
            // 连接前清空会话信息
            mqtt.setCleanSession(true);
            // 设置重新连接的次数
            mqtt.setReconnectAttemptsMax(RECONNECTION_ATTEMPT_MAX);
            // 设置重连的间隔时间
            mqtt.setReconnectDelay(RECONNECTION_DELAY);
            // 设置心跳时间
            mqtt.setKeepAlive(KEEP_ALIVE);
            // 设置缓冲的大小
            mqtt.setSendBufferSize(SEND_BUFFER_SIZE);
            //设置客户端id
            mqtt.setClientId(CLIENT_ID);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        final FutureConnection connection = mqtt.futureConnection();
        connection.connect();
        connection.subscribe(topics);
        while (true) {
            Future<Message> futrueMessage = connection.receive();
            Message message = null;
            try {
                message = futrueMessage.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("MQTTFutureClient.Receive Message " + "Topic Title :" + message.getTopic() + " context :"
                    + String.valueOf(message.getPayloadBuffer()));
        }

    }

}
