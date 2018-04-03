package com.app.androidkt.mqtt;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_LOGIN){
            if (resultCode == RESULT_OK){
                pahoMqttClient = new PahoMqttClient();

                publishMessage = (Button) findViewById(R.id.publishMessage);

                Constants.TMP_MQTT_BROKER_URL = Constants.HEADING + data.getStringExtra("LOGIN_BROKER") + Constants.PORT;
                Constants.TMP_CLIENT_ID = data.getStringExtra("LOGIN_CLIENT");
                Constants.TMP_PUBLISH_TOPIC_CMD = data.getStringExtra("LOGIN_PUB");
                Constants.TMP_SUBSCRIBE_TOPIC_CMD = data.getStringExtra("LOGIN_SUB");
                Log.d("RESULT", Constants.TMP_MQTT_BROKER_URL + ":" + Constants.TMP_CLIENT_ID + ":" + Constants.TMP_PUBLISH_TOPIC_CMD+ "/" + Constants.TMP_SUBSCRIBE_TOPIC_CMD);

//                client = pahoMqttClient.getMqttClient(getApplicationContext(), Constants.MQTT_BROKER_URL, Constants.CLIENT_ID);
                client = pahoMqttClient.getMqttClient(getApplicationContext(), Constants.TMP_MQTT_BROKER_URL, Constants.TMP_CLIENT_ID);

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
                                pahoMqttClient.publishMessage(client, msg, 2, Constants.TMP_PUBLISH_TOPIC_CMD);
                                Toast.makeText(MainActivity.this, "Launch Successfully", Toast.LENGTH_LONG).show();
                            } catch (MqttException e) {
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        NotificationCompat.Builder mBuilder =
                                new NotificationCompat.Builder(MainActivity.this)
                                        .setSmallIcon(R.drawable.uav01_alpha)
                                        .setContentTitle("Launch Successfully")
                                        .setContentText(Constants.UAV_NAME + " Launch Successfully");

                        NotificationManager mNotificationManager =
                                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        mNotificationManager.notify(101, mBuilder.build());
                        mNotificationManager.cancel(100);
                    }
                });

                subscribe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        String topic = Constants.SUBSCRIBE_TOPIC_CMD;
                        String topic = Constants.TMP_SUBSCRIBE_TOPIC_CMD;
                        if (!topic.isEmpty()) {
                            try {

                                pahoMqttClient.subscribe(client, topic, 1);
                                if(client.isConnected()){
                                    Toast.makeText(MainActivity.this, "System Activated", Toast.LENGTH_LONG).show();
                                    subscribe.setVisibility(View.GONE);
                                }else{
                                    Toast.makeText(MainActivity.this, "System Unactivated", Toast.LENGTH_LONG).show();
                                }
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

    public void setup(View view) throws MqttException {
        if(client.isConnected()){
//            Toast.makeText(MainActivity.this, "UnSubscribe", Toast.LENGTH_LONG).show();
            pahoMqttClient.unSubscribe(client, Constants.TMP_SUBSCRIBE_TOPIC_CMD);
//            pahoMqttClient.disconnect(client);
        }else{
//            Toast.makeText(MainActivity.this, "Do Nothing", Toast.LENGTH_LONG).show();
        }

        subscribe.setVisibility(View.VISIBLE);

        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }
}
