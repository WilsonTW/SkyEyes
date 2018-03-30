package com.app.androidkt.mqtt;

/**
 * Created by brijesh on 20/4/17.
 */

public class Constants {

    public static final String MQTT_BROKER_URL = "tcp://172.20.10.21:1883";

//    public static final String PUBLISH_TOPIC = "WKS/SkyEyes/StationUser/Notification";

    public static final String PUBLISH_TOPIC_CMD = "WKS/SkyEyes/UAV_AgentService/Receiver";

    public static final String SUBSCRIBE_TOPIC_CMD = "WKS/SkyEyes/StationUser/Notification";

    public static final String CLIENT_ID = "android_WKS";
}

