package com.app.androidkt.mqtt;

import android.app.Application;

/**
 * Created by Wilson on 2018/03/31.
 */

public class Constants extends Application {

    public static final String MQTT_BROKER_URL = "172.20.10.21";
    public static final String HEADING = "tcp://";
    public static final String PORT = ":1883";
    public static final String PUBLISH_TOPIC_CMD = "WKS/SkyEyes/UAV_AgentService/Receiver";
    public static final String SUBSCRIBE_TOPIC_CMD = "WKS/SkyEyes/StationUser/Notification";
    public static final String CLIENT_ID = "android_WKS";

    public static String TMP_MQTT_BROKER_URL = "";
    public static String TMP_PUBLISH_TOPIC_CMD = "";
    public static String TMP_SUBSCRIBE_TOPIC_CMD = "";
    public static String TMP_CLIENT_ID = "";

    public static String UAV_NAME = "";

//    public String getUAVName(){
//        return UAV_NAME;
//    }
}

