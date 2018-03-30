package com.app.androidkt.mqtt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

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
    private Button publishMessage;
    private Button subscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!logon){ //如未登入, 則開啟LoginActivity
            Intent intent = new Intent(this, SettingActivity.class);
            startActivityForResult(intent, RC_LOGIN);
        }

        subscribe = (Button) findViewById(R.id.subscribe);
//        setting = (Button) findViewById(R.id.setting);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_LOGIN){
            if (resultCode == RESULT_OK){
                pahoMqttClient = new PahoMqttClient();

                publishMessage = (Button) findViewById(R.id.publishMessage);

                final String MQTT_BROKER_URL = Constants.HEADING + data.getStringExtra("LOGIN_BROKER") + Constants.PORT;
                final String CLIENT_ID = data.getStringExtra("LOGIN_CLIENT");
                final String PUBLISH_TOPIC_CMD = data.getStringExtra("LOGIN_PUB");
                final String SUBSCRIBE_TOPIC_CMD = data.getStringExtra("LOGIN_SUB");
                Log.d("RESULT", MQTT_BROKER_URL + "/" + CLIENT_ID + "/" + PUBLISH_TOPIC_CMD+ "/" + SUBSCRIBE_TOPIC_CMD);

//                client = pahoMqttClient.getMqttClient(getApplicationContext(), Constants.MQTT_BROKER_URL, Constants.CLIENT_ID);
                client = pahoMqttClient.getMqttClient(getApplicationContext(), MQTT_BROKER_URL, CLIENT_ID);

                publishMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("UAV_NAME", Constants.UAV_NAME);
                        String msg = "{\n" +
                                "  \"command\": \"run_mission_plan\",\n" +
                                "  \"respond_topic\": \"\",\n" +
                                "  \"id\": \"b81bbf30-6828-4d6a-b668-5819ff679df3\",\n" +
                                "  \"parameters\": {\n" +
                                "    \"uav_name\": \"" +Constants.UAV_NAME+ "\"" +
                                "  }\n" +
                                "}";

                        if (!msg.isEmpty()) {
                            try {
//                                pahoMqttClient.publishMessage(client, msg, 1, Constants.PUBLISH_TOPIC_CMD);
                                pahoMqttClient.publishMessage(client, msg, 1, PUBLISH_TOPIC_CMD);
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
//                        String topic = Constants.SUBSCRIBE_TOPIC_CMD;
                        String topic = SUBSCRIBE_TOPIC_CMD;
                        if (!topic.isEmpty()) {
                            try {
                                pahoMqttClient.subscribe(client, topic, 1);
                            } catch (MqttException e) {
                                e.printStackTrace();
                            }
                        }
//                        v.performClick();
                    }
                });

                Intent intent = new Intent(MainActivity.this, MqttMessageService.class);
                startService(intent);

            }else{
                finish();
            }
        }
    }

    public void setup(View view){
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }
}
