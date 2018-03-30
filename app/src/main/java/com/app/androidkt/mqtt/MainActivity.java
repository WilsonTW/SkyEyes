package com.app.androidkt.mqtt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
    public static final int RC_LOGIN = 1;
    boolean logon = false;

    private MqttAndroidClient client;
    private String TAG = "MainActivity";
    private PahoMqttClient pahoMqttClient;

//    private EditText textMessage, subscribeTopic, unSubscribeTopic;
    private Button publishMessage, subscribe, unSubscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!logon){ //如未登入, 則開啟LoginActivity
            Intent intent = new Intent(this, SettingActivity.class);
            startActivityForResult(intent, RC_LOGIN);
        }
//        pahoMqttClient = new PahoMqttClient();
//
////        textMessage = (EditText) findViewById(R.id.textMessage);
//        publishMessage = (Button) findViewById(R.id.publishMessage);
//
//        subscribe = (Button) findViewById(R.id.subscribe);
//        unSubscribe = (Button) findViewById(R.id.unSubscribe);
//
////        subscribeTopic = (EditText) findViewById(R.id.subscribeTopic);
////        unSubscribeTopic = (EditText) findViewById(R.id.unSubscribeTopic);
//        client = pahoMqttClient.getMqttClient(getApplicationContext(), Constants.MQTT_BROKER_URL, Constants.CLIENT_ID);
//
//        publishMessage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String msg = "{\n" +
//                        "  \"command\": \"run_mission_plan\",\n" +
//                        "  \"respond_topic\": \"\",\n" +
//                        "  \"id\": \"b81bbf30-6828-4d6a-b668-5819ff679df3\",\n" +
//                        "  \"parameters\": {\n" +
//                        "    \"uav_name\": \"EMU-101\"\n" +
//                        "  }\n" +
//                        "}";
//
////                JSONObject jsonObject = new JSONObject(jsonText);
//
//                if (!msg.isEmpty()) {
//                    try {
//                        pahoMqttClient.publishMessage(client, msg, 1, Constants.PUBLISH_TOPIC_CMD);
//                    } catch (MqttException e) {
//                        e.printStackTrace();
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//                }
////                String msg = textMessage.getText().toString().trim();
////                if (!msg.isEmpty()) {
////                    try {
////                        pahoMqttClient.publishMessage(client, msg, 1, Constants.PUBLISH_TOPIC);
////                    } catch (MqttException e) {
////                        e.printStackTrace();
////                    } catch (UnsupportedEncodingException e) {
////                        e.printStackTrace();
////                    }
////                }
//            }
//        });
//
//        subscribe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                String topic = subscribeTopic.getText().toString().trim();
//                String topic = Constants.SUBSCRIBE_TOPIC_CMD;
//                if (!topic.isEmpty()) {
//                    try {
//                        pahoMqttClient.subscribe(client, topic, 1);
//                    } catch (MqttException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        unSubscribe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                String topic = unSubscribeTopic.getText().toString().trim();
//                String topic = Constants.SUBSCRIBE_TOPIC_CMD;
//                if (!topic.isEmpty()) {
//                    try {
//                        pahoMqttClient.unSubscribe(client, topic);
//                    } catch (MqttException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//
//        Intent intent = new Intent(MainActivity.this, MqttMessageService.class);
//        startService(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_LOGIN){
            if (resultCode == RESULT_OK){
                pahoMqttClient = new PahoMqttClient();

                publishMessage = (Button) findViewById(R.id.publishMessage);

                subscribe = (Button) findViewById(R.id.subscribe);
                unSubscribe = (Button) findViewById(R.id.unSubscribe);

                String MQTT_BROKER_URL = data.getStringExtra("LOGIN_BROKER");
                String CLIENT_ID = data.getStringExtra("LOGIN_CLIENT");
                String PUBLISH_TOPIC_CMD = data.getStringExtra("LOGIN_PUB");
                String SUBSCRIBE_TOPIC_CMD = data.getStringExtra("LOGIN_SUB");
                Log.d("RESULT", MQTT_BROKER_URL + "/" + CLIENT_ID + "/" + PUBLISH_TOPIC_CMD+ "/" + SUBSCRIBE_TOPIC_CMD);

                client = pahoMqttClient.getMqttClient(getApplicationContext(), Constants.MQTT_BROKER_URL, Constants.CLIENT_ID);

                publishMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String msg = "{\n" +
                                "  \"command\": \"run_mission_plan\",\n" +
                                "  \"respond_topic\": \"\",\n" +
                                "  \"id\": \"b81bbf30-6828-4d6a-b668-5819ff679df3\",\n" +
                                "  \"parameters\": {\n" +
                                "    \"uav_name\": \"EMU-101\"\n" +
                                "  }\n" +
                                "}";

                        if (!msg.isEmpty()) {
                            try {
                                pahoMqttClient.publishMessage(client, msg, 1, Constants.PUBLISH_TOPIC_CMD);
                            } catch (MqttException e) {
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

                subscribe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String topic = Constants.SUBSCRIBE_TOPIC_CMD;
                        if (!topic.isEmpty()) {
                            try {
                                pahoMqttClient.subscribe(client, topic, 1);
                            } catch (MqttException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                unSubscribe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String topic = Constants.SUBSCRIBE_TOPIC_CMD;
                        if (!topic.isEmpty()) {
                            try {
                                pahoMqttClient.unSubscribe(client, topic);
                            } catch (MqttException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

                Intent intent = new Intent(MainActivity.this, MqttMessageService.class);
                startService(intent);

            }else{
                finish();
            }
        }
    }
}
