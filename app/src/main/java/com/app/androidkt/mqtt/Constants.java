package com.app.androidkt.mqtt;

import android.app.Application;

/**
 * Created by brijesh on 20/4/17.
 */

public class Constants extends Application {

    public static final String MQTT_BROKER_URL = "172.20.10.21";

    public static final String HEADING = "tcp://";

    public static final String PORT = ":1883";

//    public static final String PUBLISH_TOPIC = "WKS/SkyEyes/StationUser/Notification";

    public static final String PUBLISH_TOPIC_CMD = "WKS/SkyEyes/UAV_AgentService/Receiver";

    public static final String SUBSCRIBE_TOPIC_CMD = "WKS/SkyEyes/StationUser/Notification";

    public static final String CLIENT_ID = "android_WKS";

    public static String UAV_NAME = "";

//    public String getUAVName(){
//        return UAV_NAME;
//    }
}

