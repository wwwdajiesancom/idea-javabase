package com.loujie.www.server;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

import java.net.URISyntaxException;

public class MqttClientUtils {

    //    private static final String host = "118.144.248.25";
//    private static final int port = 1883;
    private static final String host = "mq.pbsedu.com";
    private static final int port = 2883;
    private static final String username = "server";
    private static final String password = "server1";

    private final static boolean CLEAN_START = true;
    private final static String CLIENT_ID = "server";
    private final static short KEEP_ALIVE = 30;// 低耗网络，但是又需要及时获取数据，心跳30s

    private static Topic[] topics2 = {
            new Topic("jiaoyubao/+/+", QoS.EXACTLY_ONCE), // 2  只有一次
            new Topic("jiaoyubao/login/+", QoS.AT_LEAST_ONCE),  // 1 至少一次
            new Topic("jiaoyubao/open_order/+", QoS.AT_MOST_ONCE)};  // 0 至多一次

    private final static long RECONNECTION_ATTEMPT_MAX = 6;
    private final static long RECONNECTION_DELAY = 2000;

    private final static int SEND_BUFFER_SIZE = 64;// 发送最大缓冲

    private static MQTT mqtt = null;

    static {
        mqtt = new MQTT();
        //==MQTT设置说明
        try {
            //设置服务端的ip
            mqtt.setHost(host, port);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        //连接前清空会话信息 ,若设为false，MQTT服务器将持久化客户端会话的主体订阅和ACK位置，默认为true
        mqtt.setCleanSession(CLEAN_START);
        //设置心跳时间 ,定义客户端传来消息的最大时间间隔秒数，服务器可以据此判断与客户端的连接是否已经断开，从而避免TCP/IP超时的长时间等待
        mqtt.setKeepAlive(KEEP_ALIVE);
        //设置客户端id,用于设置客户端会话的ID。在setCleanSession(false);被调用时，MQTT服务器利用该ID获得相应的会话。
        //此ID应少于23个字符，默认根据本机地址、端口和时间自动生成
        mqtt.setClientId(CLIENT_ID);

//        mqtt.setUserName(username);
//        mqtt.setPassword(password);

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
    }

    /**
     * 发布消息
     *
     * @param message 要发送的消息
     * @param topic   主题
     * @return
     */
    public static boolean publish(String message, String topic) {
        mqtt.callbackConnection();
        BlockingConnection blockingConnection = mqtt.blockingConnection();
        try {
            blockingConnection.connect();
            blockingConnection.publish(topic, message.getBytes(), QoS.AT_LEAST_ONCE, false);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (blockingConnection != null) {
                    blockingConnection.disconnect();
                }
            } catch (Exception e) {
            }
        }
        return true;
    }

}
